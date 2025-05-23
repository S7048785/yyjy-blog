package com.yyjy.service;

import com.yyjy.result.PageResult;
import com.yyjy.domain.vo.request.CommentPageReq;
import com.yyjy.domain.vo.request.CommentReq;
import com.yyjy.domain.vo.response.CommentRes;

public interface CommentService {
	PageResult<CommentRes> list(CommentPageReq req);

	CommentRes create(CommentReq req);

	void delete(Long id);
}
