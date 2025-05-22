package com.yyjy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.domain.vo.request.ArticlePageReq;
import com.yyjy.domain.vo.response.ArticleCardRes;
import com.yyjy.domain.vo.response.ArticleHotRes;
import com.yyjy.domain.vo.response.ArticleRes;

import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author yyjy
 * @since 2025-05-06
 */
public interface ArticleService{

	List<ArticleHotRes> hotArticleList();

	Page<ArticleCardRes> pageArticleList(ArticlePageReq req);

	Page<ArticleCardRes> pageCacheArticleList(ArticlePageReq req);

	ArticleRes getArticleById(Long id);

	void likeArticle(Long id);

	void viewArticle(Long id);

	Boolean isLiked(Long id);
}
