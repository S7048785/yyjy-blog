package com.yyjy.web.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.common.constant.CacheConstant;
import com.yyjy.common.context.BaseContext;
import com.yyjy.common.utils.CacheUtil;
import com.yyjy.web.dao.ArticleDao;
import com.yyjy.web.dao.ArticleLikeDao;
import com.yyjy.web.domain.entity.Article;
import com.yyjy.web.domain.entity.ArticleLike;
import com.yyjy.web.domain.vo.request.ArticlePageReq;
import com.yyjy.web.domain.vo.response.ArticleCardRes;
import com.yyjy.web.domain.vo.response.ArticleHotRes;
import com.yyjy.web.domain.vo.response.ArticleRes;
import com.yyjy.web.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Resource
	private ArticleDao articleDao;

	@Resource
	private ArticleLikeDao articleLikeDao;

	@Resource
	private CacheUtil cacheUtil;

	// 线程池
	private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

	@Override
	public List<ArticleHotRes> hotArticleList() {
		return null;
	}

	@Override
	public Page<ArticleCardRes> pageArticleList(ArticlePageReq req) {

		while (true) {
			// 查询redis
			Collection<String> sortedSetByScore = cacheUtil.getSortedSetByScore(CacheConstant.CACHE_ARTICLE_LIST, req.getMax(), req.getCurrent() == 1 ? 0 : 1, req.getSize());

			// 缓存命中
			if (CollUtil.isNotEmpty(sortedSetByScore)) {
				List<ArticleCardRes> articleCardRes = JSONUtil.toList(sortedSetByScore.toString(), ArticleCardRes.class);
				Long total = cacheUtil.getSortedSetCount(CacheConstant.CACHE_ARTICLE_LIST);
				return new Page<ArticleCardRes>(req.getCurrent(), req.getSize()).setRecords(articleCardRes).setTotal(total);
			}

			// 未命中，尝试获取锁
			boolean b = cacheUtil.tryLock(CacheConstant.CACHE_ARTICLE_LIST_LOCK);
			if (b) {
				try {
					// 成功获取
					Page<ArticleCardRes> articleCardResPage = new Page<>(req.getCurrent(), req.getSize());
					articleCardResPage = articleDao.page(articleCardResPage, req);

					if (CollUtil.isNotEmpty(articleCardResPage.getRecords())) {
						// 异步存入redis

						Page<ArticleCardRes> finalArticleCardResPage = articleCardResPage;
						CACHE_REBUILD_EXECUTOR.submit(() -> {
								Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
								// 存入redis
								finalArticleCardResPage.getRecords().forEach(item -> {
									DefaultTypedTuple<String> articleCardResDefaultTypedTuple = new DefaultTypedTuple<>(JSONUtil.toJsonStr(item), Double.parseDouble(item.getCreateTime()));
									set.add(articleCardResDefaultTypedTuple);
								});
								cacheUtil.setSortedSetByScore(CacheConstant.CACHE_ARTICLE_LIST, set);
						});

//						Page<ArticleCardRes> finalArticleCardResPage = articleCardResPage;

					}
					return articleCardResPage;
				} finally {
					// 释放锁
					cacheUtil.unlock(CacheConstant.CACHE_ARTICLE_LIST_LOCK);
				}
			}

		}
	}

	@Override
	public ArticleRes getArticleById(Long id) {
		String ip = BaseContext.getCurrentId();

		while (true) {
			// 查询redis
			String str = cacheUtil.getStr(CacheConstant.CACHE_ARTICLE_DETAIL + id);
			// 缓存命中
			if (StrUtil.isNotBlank(str)) {
				return JSONUtil.toBean(str, ArticleRes.class);
			}
			// 未命中，尝试获取锁
			boolean b = cacheUtil.tryLock(CacheConstant.CACHE_ARTICLE_LOCK + id);
			if (b) {
				try {
					ArticleRes articleRes = articleDao.getArticleById(id);
					// 异步存入redis
					CompletableFuture.runAsync(() -> {
						cacheUtil.setStr(CacheConstant.CACHE_ARTICLE_DETAIL + id, JSONUtil.toJsonStr(articleRes), 60 * 60);
					});
					return articleRes;
				} finally {
					cacheUtil.unlock(CacheConstant.CACHE_ARTICLE_LOCK + id);
				}
			}
		}
	}

	@Override
	public void likeArticle(Long id) {
		ArticleLike one = articleLikeDao.getOne(id, BaseContext.getCurrentId());

		if (one == null) {
			ArticleLike articleLike = new ArticleLike()
					.setArticleId(id)
					.setUserIp(BaseContext.getCurrentId());
			articleLikeDao.save(articleLike);

			// 删除缓存
			CompletableFuture.runAsync(() -> {
					cacheUtil.deleteKey(CacheConstant.CACHE_ARTICLE_DETAIL + id);
			});

			// 更新文章详情
			CompletableFuture.runAsync(() -> {
				Article article = articleDao.getById(id);
				cacheUtil.updateSortedSetByScore(CacheConstant.CACHE_ARTICLE_LIST, JSONUtil.toJsonStr(article), Double.parseDouble(article.getCreateTime()));
			});
		}

	}

	@Override
	public void viewArticle(Long id) {
		String ip = BaseContext.getCurrentId();

		synchronized (ip) {
			String hash = cacheUtil.getHash(CacheConstant.CACHE_ARTICLE_VIEW_IP + id, ip);
			if (StrUtil.isNotBlank(hash)) {
				return;
			}

			// 更新浏览量
			articleDao.update(Wrappers.lambdaUpdate(Article.class)
					.eq(Article::getId, id)
					.setSql("view_count = view_count + 1"));

			// 更新redis
			ArticleRes a = articleDao.getArticleById(id);
			cacheUtil.updateSortedSetByScore(CacheConstant.CACHE_ARTICLE_LIST, JSONUtil.toJsonStr(a), Double.parseDouble(a.getCreateTime()));
			// TODO 查询文章卡片，更新redis
			ArticleRes article = articleDao.getArticleById(id);
			cacheUtil.setHash(CacheConstant.CACHE_ARTICLE_DETAIL, id.toString(), JSONUtil.toJsonStr(article));

			cacheUtil.setHash(CacheConstant.CACHE_ARTICLE_VIEW_IP + id, ip, "1");
		}

	}

	@Override
	public Boolean isLiked(Long id) {
		if (articleLikeDao.exists(BaseContext.getCurrentId(), id)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
