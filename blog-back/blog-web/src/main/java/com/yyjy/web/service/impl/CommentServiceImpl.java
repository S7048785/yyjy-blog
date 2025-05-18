package com.yyjy.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.common.constant.CacheConstant;
import com.yyjy.common.context.BaseContext;
import com.yyjy.common.result.PageResult;
import com.yyjy.common.utils.CacheUtil;
import com.yyjy.web.dao.CommentDao;
import com.yyjy.web.domain.entity.Comment;
import com.yyjy.web.domain.vo.request.CommentPageReq;
import com.yyjy.web.domain.vo.request.CommentReq;
import com.yyjy.web.domain.vo.response.ArticleCardRes;
import com.yyjy.web.domain.vo.response.CommentRes;
import com.yyjy.web.mapper.CommentMapper;
import com.yyjy.web.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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

	@Override
	public PageResult<CommentRes> list(CommentPageReq req) {
		List<CommentRes> list = commentDao.page((req.getCurrent() - 1) * req.getSize(), req.getSize(), req);
		// 构建评论树
		Map<Long, CommentRes> map = new HashMap<>();
		List<CommentRes> result = new ArrayList<>();

		// 存入所有评论
		for (CommentRes commentRes : list) {
			if (commentRes.getParentId() == null) {
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
		comment.setCreateTime(LocalDateTime.now());
		if ("陈九`".equals(req.getNickName().trim())) {
			comment.setNickName("陈九").setIsAuthor(1);
		}
		// 修改IP为属地
		comment.setIpAddress(getIpAddress(BaseContext.getCurrentId()));

		commentDao.save(comment);

		// 更新缓存
		CompletableFuture.runAsync(() -> {
			long count = commentDao.count(req.getArticleId());
			cacheUtil.setHash(CacheConstant.CACHE_ARTICLE_DETAIL + req.getArticleId(), "commentCount", String.valueOf(count));
		});

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
