package com.yyjy.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.common.result.PageResult;
import com.yyjy.common.result.Result;
import com.yyjy.web.domain.vo.request.ArticlePageReq;
import com.yyjy.web.domain.vo.response.ArticleHotRes;
import com.yyjy.web.domain.vo.response.ArticleCardRes;
import com.yyjy.web.domain.vo.response.ArticleRes;
import com.yyjy.web.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "文章管理")
@RestController
@RequestMapping("/article")
public class ArticleController {

	@Resource
	private ArticleService articleService;

	@Operation(summary = "获取最热文章top10")
	@GetMapping("/hot")
	public Result<List<ArticleHotRes>> hotArticleList() {
		List<ArticleHotRes> list = articleService.hotArticleList();
		return Result.ok(list);
	}

	@Operation(summary = "分页获取文章列表")
	@GetMapping("/list")
	public PageResult<ArticleCardRes> pageArticleList(ArticlePageReq req) {
		Page<ArticleCardRes> page = articleService.pageArticleList(req);
		return new PageResult<>(page.getTotal(), page.getRecords(), req.getCurrent(), req.getSize());
	}

	@Operation(summary = "获取文章详情")
	@GetMapping("{id}")
	public Result<ArticleRes> getArticleById(@PathVariable Long id) {
		ArticleRes articleRes = articleService.getArticleById(id);
		return Result.ok(articleRes);
	}

	@Operation(summary = "点赞文章")
	@PostMapping("/like/{id}")
	public Result<Void> likeArticle(@PathVariable Long id) {
		articleService.likeArticle(id);
		return Result.ok();
	}
}
