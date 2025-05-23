package com.yyjy.controller;


import com.yyjy.domain.vo.request.ArticleDetailReq;
import com.yyjy.domain.vo.request.ArticleListReq;
import com.yyjy.domain.vo.response.ArticleColRes;
import com.yyjy.result.Result;
import com.yyjy.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "文章管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/article")
public class ArticleController {

	private final ArticleService articleService;
	@Operation(summary = "获取文章列表")
	@GetMapping("/list")
	public Result<List<ArticleColRes>> list(ArticleListReq req) {
		List<ArticleColRes> list = articleService.list(req);
		return Result.ok(list);
	}

	@Operation(summary = "删除文章")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@PathVariable Long id) {
		Boolean b = articleService.removeById(id);
		return Result.ok(b);
	}

	@Operation(summary = "批量删除文章")
	@DeleteMapping("/batch")
	public Result<Boolean> batchDelete(@RequestBody List<Long> ids) {
		Boolean b = articleService.removeBatchByIds(ids);
		return Result.ok(b);
	}

	@Operation(summary = "添加文章")
	@PostMapping
	public Result<Void> add(@RequestBody @Validated ArticleDetailReq req) {
		articleService.save(req);
		return Result.ok();
	}
}
