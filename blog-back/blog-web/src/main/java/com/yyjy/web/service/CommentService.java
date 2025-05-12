package com.yyjy.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.web.domain.vo.request.CommentPageReq;
import com.yyjy.web.domain.vo.request.CommentReq;
import com.yyjy.web.domain.vo.response.CommentRes;
import org.springframework.stereotype.Service;

public interface CommentService {
	Page<CommentRes> list(CommentPageReq req);

	void create(CommentReq req);

	void delete(Long id);
}
