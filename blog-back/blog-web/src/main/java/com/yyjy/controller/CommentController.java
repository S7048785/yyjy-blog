package com.yyjy.controller;

import com.yyjy.result.PageResult;
import com.yyjy.result.Result;
import com.yyjy.domain.vo.request.CommentPageReq;
import com.yyjy.domain.vo.request.CommentReq;
import com.yyjy.domain.vo.response.CommentRes;
import com.yyjy.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
	public Result<CommentRes> create(@RequestBody CommentReq req) {
		CommentRes commentRes = commentService.create(req);
		return Result.ok(commentRes);
	}
}
