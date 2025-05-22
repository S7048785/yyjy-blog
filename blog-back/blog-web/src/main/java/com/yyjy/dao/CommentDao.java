package com.yyjy.dao;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.domain.entity.Comment;
import com.yyjy.domain.vo.request.CommentPageReq;
import com.yyjy.domain.vo.response.CommentRes;
import com.yyjy.mapper.CommentMapper;
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

	public long count(Long articleId) {
		return count(Wrappers.lambdaQuery(Comment.class)
				.eq(Comment::getArticleId, articleId));
	}

	public long total(Long articleId) {
		return count(Wrappers.lambdaQuery(Comment.class).eq(Comment::getArticleId, articleId).isNull(Comment::getRootParentId));
	}
}
