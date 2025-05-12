package com.yyjy.web.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.web.domain.entity.Comment;
import com.yyjy.web.domain.vo.request.CommentPageReq;
import com.yyjy.web.domain.vo.response.CommentRes;
import com.yyjy.web.mapper.CommentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CommentDao extends ServiceImpl <CommentMapper, Comment>{

	@Resource
	private CommentMapper commentMapper;


	public Page<CommentRes> page(Page<CommentRes> commentResPage, CommentPageReq req) {
		return commentMapper.page(commentResPage, req);
	}
}
