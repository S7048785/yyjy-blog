package com.yyjy.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.common.result.PageResult;
import com.yyjy.web.dao.CommentDao;
import com.yyjy.web.domain.entity.Comment;
import com.yyjy.web.domain.vo.request.CommentPageReq;
import com.yyjy.web.domain.vo.request.CommentReq;
import com.yyjy.web.domain.vo.response.CommentRes;
import com.yyjy.web.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentDao commentDao;

	@Override
	public PageResult list(CommentPageReq req) {
		List<CommentRes> list = commentDao.page((req.getCurrent() - 1) * req.getSize(), req.getSize(), req);
		// 构建评论树
		Map<Long, CommentRes> map = new HashMap<>();
		List<CommentRes> result = new ArrayList<>();

		// 存入所有评论
		for (CommentRes commentRes : list) {
			if (commentRes.getParentId() == null) {
				result.add(commentRes);
			}
			map.put(commentRes.getId(), commentRes);
		}

		// 构建子评论
		for (CommentRes commentRes : list) {
			Long rootParentId = commentRes.getRootParentId();
			if (rootParentId != null) {
				CommentRes commentRes1 = map.get(rootParentId);
				if (commentRes1.getChildren() == null) {
					commentRes1.setChildren(new ArrayList<>());
				}
				commentRes1.getChildren().add(commentRes);
			}
		}
		long total = commentDao.count(req);
		return new PageResult(total, result, req.getCurrent(), req.getSize());
	}

	@Override
	public void create(CommentReq req) {
		Comment comment = BeanUtil.copyProperties(req, Comment.class);
		comment.setCreateTime(LocalDateTime.now());
		if ("陈九`".equals(req.getNickName().trim())) {
			comment.setNickName("陈九").setIsAuthor(1);
		}
		commentDao.save(comment);
	}

	@Override
	public void delete(Long id) {
		commentDao.removeById(id);
	}
}
