package com.yyjy.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
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
		Page<ArticleCardRes> articleCardResPage = new Page<>(req.getCurrent(), req.getSize());
		return articleDao.page(articleCardResPage, req);

	}

	@Override
	public ArticleRes getArticleById(Long id) {

		String ip = BaseContext.getCurrentId();
		// 查询redis
		Map<Object, Object> hash = cacheUtil.getHashAll(CacheConstant.CACHE_ARTICLE_DETAIL + id);
		// 缓存命中
		if (hash != null) {
			return BeanUtil.toBean(hash, ArticleRes.class);
		}

		ArticleRes articleRes = articleDao.getArticleById(id);

		// 异步存入redis
		CompletableFuture.runAsync(() -> {
			HashMap<String, String> map = new HashMap<>();
			BeanUtil.beanToMap(articleRes).forEach((key, value) -> {
				map.put(key, value.toString());
			});
			cacheUtil.setHashAll(CacheConstant.CACHE_ARTICLE_DETAIL + id.toString(), map);
		});
		return articleRes;
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
		ip = ip.replaceAll(":", "-");
		synchronized (ip) {
			Boolean result = cacheUtil.setIfAbsent(CacheConstant.CACHE_ARTICLE_VIEW_ID + id + ":" + ip, "1");
			if (!result) {
				return;
			}

			String hash1 = cacheUtil.getHash(CacheConstant.CACHE_ARTICLE_DETAIL + id, "viewCount");

			if (StrUtil.isNotBlank(hash1)) {
				cacheUtil.setHash(CacheConstant.CACHE_ARTICLE_DETAIL + id, "viewCount", String.valueOf(Integer.parseInt(hash1) + 1));
				cacheUtil.setStr(CacheConstant.CACHE_ARTICLE_VIEW_ID + id + ":" + ip, "1");
			}

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
