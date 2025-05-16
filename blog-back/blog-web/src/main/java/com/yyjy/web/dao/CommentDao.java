package com.yyjy.web.dao;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.web.domain.entity.Comment;
import com.yyjy.web.domain.vo.request.CommentPageReq;
import com.yyjy.web.domain.vo.response.CommentRes;
import com.yyjy.web.mapper.CommentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentDao extends ServiceImpl <CommentMapper, Comment>{

	@Resource
	private CommentMapper commentMapper;


	public List<CommentRes> page(long current, long size, CommentPageReq req) {
		return commentMapper.page(current, size, req);
	}

	public long count(CommentPageReq req) {
		return count(Wrappers.lambdaQuery(Comment.class).eq(Comment::getArticleId, req.getArticleId()).isNull(Comment::getParentId));
	}
}
