package com.yyjy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import com.yyjy.constant.CacheConstant;
import com.yyjy.context.BaseContext;
import com.yyjy.result.PageResult;
import com.yyjy.utils.CacheUtil;
import com.yyjy.dao.CommentDao;
import com.yyjy.domain.entity.Comment;
import com.yyjy.domain.vo.request.CommentPageReq;
import com.yyjy.domain.vo.request.CommentReq;
import com.yyjy.domain.vo.response.CommentRes;
import com.yyjy.mapper.CommentMapper;
import com.yyjy.service.CommentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

	String regexp1 = "\"location\":\"(.*?)\"";
	String regexp2 = "(.*?)(?=省|市)";

	@Resource
	private CommentDao commentDao;
	@Autowired
	private CacheUtil cacheUtil;
	@Autowired
	private CommentMapper commentMapper;
	@Value("${blog.adminName}")
	private String name;

	@Override
	public PageResult<CommentRes> list(CommentPageReq req) {
		List<CommentRes> list = commentDao.page((req.getCurrent() - 1) * req.getSize(), req.getSize(), req);
		// 构建评论树
		Map<Long, CommentRes> map = new HashMap<>();
		List<CommentRes> result = new ArrayList<>();

		// 存入所有评论
		for (CommentRes commentRes : list) {
			if (commentRes.getRootParentId() == null) {
				commentRes.setChildren(new ArrayList<>());
				result.add(commentRes);
			}
			map.put(commentRes.getId(), commentRes);
		}

		// 构建子评论
		for (CommentRes commentRes : list) {
			Long rootParentId = commentRes.getRootParentId();

			if (rootParentId != null) {
				CommentRes commentRes1 = map.get(rootParentId);
				commentRes1.getChildren().add(commentRes);
			}
		}
		long total = commentDao.total(req.getArticleId());
		return new PageResult<>(total, result, req.getCurrent(), req.getSize());
	}

	@Override
	public CommentRes create(CommentReq req) {
		Comment comment = BeanUtil.copyProperties(req, Comment.class);
		comment.setCreateTime(DateUtil.now());
		if (name.equals(req.getNickName().trim())) {
			comment.setNickName("陈九").setIsAuthor(1);
		}
		// 修改IP为属地
		String ip = BaseContext.getCurrentId();
		String ipAddress = getIpAddress(ip);
		log.info("IP:{}, 属地:{}", ip, ipAddress);
		comment.setIpAddress(ipAddress);
		comment.setIp(ip);

		commentDao.save(comment);

		// 更新缓存
		CompletableFuture.runAsync(() -> {
			long count = commentDao.count(req.getArticleId());
			cacheUtil.setHash(CacheConstant.CACHE_ARTICLE_DETAIL + req.getArticleId(), "commentCount", String.valueOf(count));
		});
		comment.setCreateTime(String.valueOf((int)(System.currentTimeMillis() / 1000)));
		return BeanUtil.copyProperties(comment, CommentRes.class);
	}

	@Override
	public void delete(Long id) {
		commentDao.removeById(id);
	}


	/**
	 * 获取ip属地
	 */
	public String getIpAddress(String ip) {

		if (!ReUtil.isMatch("\\d{1,3}:\\d{1,3}:\\d{1,3}:\\d{1,3}", ip)) {
			return "未知";
		}

		Map<String, Object> query = Map.of("query", ip, "resource_id", 6006, "oe", "utf8");

		String s = null;
		try {
			s = HttpUtil.get("https://opendata.baidu.com/api.php", query);
		} catch (Exception e) {
			return "未知";
		}

		// 拿到属地信息
		String location = ReUtil.getGroup1(regexp1, s);

		// 获取 省份 或 地级市
		String address = ReUtil.getGroup0(regexp2, location);

		if (address == null)
			// 返回地区
			return location;
		// 返回省份 或 地级市
		return address;
	}
}
