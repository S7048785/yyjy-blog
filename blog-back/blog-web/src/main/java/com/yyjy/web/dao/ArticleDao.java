package com.yyjy.web.dao;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.web.domain.entity.Article;
import com.yyjy.web.domain.vo.request.ArticlePageReq;
import com.yyjy.web.domain.vo.response.ArticleCardRes;
import com.yyjy.web.domain.vo.response.ArticleRes;
import com.yyjy.web.mapper.ArticleMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Page<ArticleCardRes> page(Page<ArticleCardRes> page,  ArticlePageReq req) {
		return articleMapper.page(page, req);
	}

	public ArticleRes getArticleById(Long id) {
		return articleMapper.getArticleById(id);
	}
}
