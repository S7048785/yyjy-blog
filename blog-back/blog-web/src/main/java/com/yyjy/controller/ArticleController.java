package com.yyjy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.context.BaseContext;
import com.yyjy.result.PageResult;
import com.yyjy.result.Result;
import com.yyjy.domain.vo.request.ArticlePageReq;
import com.yyjy.domain.vo.response.ArticleHotRes;
import com.yyjy.domain.vo.response.ArticleCardRes;
import com.yyjy.domain.vo.response.ArticleRes;
import com.yyjy.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
		log.info("ip: {}", BaseContext.getCurrentId());

		Page<ArticleCardRes> page = articleService.pageCacheArticleList(req);
		return new PageResult<>(page);
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

	@Operation(summary = "文章阅读量自增")
	@PostMapping("/view/{id}")
	public Result<Void> viewArticle(@PathVariable Long id) {
		articleService.viewArticle(id);
		return Result.ok();
	}

	@Operation(summary = "文章是否已点赞")
	@GetMapping("/liked-status/{id}")
	public Result<Boolean> isLiked(@PathVariable Long id) {
		return Result.ok(articleService.isLiked(id));
	}
}
