package com.yyjy.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.common.result.PageResult;
import com.yyjy.common.result.Result;
import com.yyjy.web.domain.vo.request.CommentPageReq;
import com.yyjy.web.domain.vo.request.CommentReq;
import com.yyjy.web.domain.vo.response.CommentRes;
import com.yyjy.web.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "评论管理")
@RestController
@RequestMapping("/comment")
public class CommentController {

	@Resource
	private CommentService commentService;

	@Operation(summary = "分页获取评论列表")
	@GetMapping("/list")
	public PageResult<CommentRes> list(CommentPageReq req) {
		return commentService.list(req);
	}

	@Operation(summary = "创建评论")
	@PostMapping
	public Result<Void> create(CommentReq req) {
		commentService.create(req);
		return Result.ok();
	}
}
