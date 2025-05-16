package com.yyjy.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.common.result.PageResult;
import com.yyjy.web.domain.vo.request.CommentPageReq;
import com.yyjy.web.domain.vo.request.CommentReq;
import com.yyjy.web.domain.vo.response.CommentRes;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
	PageResult<CommentRes> list(CommentPageReq req);

	void create(CommentReq req);

	void delete(Long id);
}
