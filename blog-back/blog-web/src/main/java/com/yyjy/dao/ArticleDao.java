package com.yyjy.dao;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.domain.entity.Article;
import com.yyjy.domain.vo.request.ArticlePageReq;
import com.yyjy.domain.vo.response.ArticleCardRes;
import com.yyjy.domain.vo.response.ArticleRes;
import com.yyjy.mapper.ArticleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleDao extends ServiceImpl<ArticleMapper, Article> {
	@Resource
	private ArticleMapper articleMapper;

	public Page<Article> hotList(Page<Article> articlePage) {
		return page(
				articlePage,
				Wrappers.lambdaQuery(Article.class)
						.eq(Article::getStatus, "1")
						.orderByDesc(Article::getViewCount)
		);
	}

	public List<ArticleCardRes> list(long current, long size, ArticlePageReq req) {
		return articleMapper.list(current, size, req);
	}

	public Page<ArticleCardRes> page(Page<ArticleCardRes> page,  ArticlePageReq req) {
		return articleMapper.page(page, req);
	}

	public ArticleRes getArticleById(Long id) {
		return articleMapper.getArticleById(id);
	}

	public ArticleCardRes getArticleCardById(Long id) {
		return articleMapper.getArticleCardById(id);
	}

	public synchronized void likeArticle(Long id) {
		update(Wrappers.lambdaUpdate(Article.class).eq(Article::getId, id).setSql("like_count = like_count + 1"));
	}
}
