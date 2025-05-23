package com.yyjy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.constant.CacheConstant;
import com.yyjy.context.BaseContext;
import com.yyjy.event.ArticleCardEvent;
import com.yyjy.utils.CacheUtil;
import com.yyjy.dao.ArticleDao;
import com.yyjy.dao.ArticleLikeDao;
import com.yyjy.domain.entity.Article;
import com.yyjy.domain.entity.ArticleLike;
import com.yyjy.domain.vo.request.ArticlePageReq;
import com.yyjy.domain.vo.response.ArticleCardRes;
import com.yyjy.domain.vo.response.ArticleHotRes;
import com.yyjy.domain.vo.response.ArticleRes;
import com.yyjy.service.ArticleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

	@Autowired
	private ApplicationEventPublisher applicationEventPub;
	@Override
	public Page<ArticleCardRes> pageArticleList(ArticlePageReq req) {
		Page<ArticleCardRes> articleCardResPage = new Page<>(req.getCurrent(), req.getSize());
		return articleDao.page(articleCardResPage, req);
	}

	@Override
	public Page<ArticleCardRes> pageCacheArticleList(ArticlePageReq req) {

		while (true) {
			// 查询缓存
			Collection<String> sortedSetByScore = cacheUtil.getSortedSetByScore(CacheConstant.CACHE_ARTICLE_LIST, req.getMax(), req.getCurrent() == 1 ? 0 : 1, req.getSize());
			// 缓存命中
			if (CollUtil.isNotEmpty(sortedSetByScore)) {
				List<ArticleCardRes> articleCardRes = JSONUtil.toList(sortedSetByScore.toString(), ArticleCardRes.class);
				Long total = articleDao.count();
				return new Page<ArticleCardRes>(req.getCurrent(), req.getSize()).setRecords(articleCardRes).setTotal(total);
			}
			// 未命中，尝试获取锁
			boolean b = cacheUtil.tryLock(CacheConstant.CACHE_ARTICLE_LIST_LOCK);
			if (b) {
				try {
					Page<ArticleCardRes> articleCardResPage = new Page<>(req.getCurrent(), req.getSize());
					articleCardResPage = articleDao.page(articleCardResPage, req);

					if (CollUtil.isNotEmpty(articleCardResPage.getRecords())) {
						// 异步存入redis
						Page<ArticleCardRes> finalArticleCardResPage = articleCardResPage;
						CompletableFuture.runAsync(() -> {
							Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
							// 存入redis
							finalArticleCardResPage.getRecords().forEach(item -> {
								DefaultTypedTuple<String> articleCardResDefaultTypedTuple = new DefaultTypedTuple<>(JSONUtil.toJsonStr(item), Double.parseDouble(item.getCreateTime()));
								set.add(articleCardResDefaultTypedTuple);
							});
							cacheUtil.setSortedSetByScore(CacheConstant.CACHE_ARTICLE_LIST, set);
						});
						return articleCardResPage;
					}
				} catch (Exception e) {
					log.error("缓存异常{}", e.getMessage());
				}
				finally {
					cacheUtil.unlock(CacheConstant.CACHE_ARTICLE_LIST_LOCK);
				}
			}
		}
	}


	/**
	 * 获取文章详情
	 * @param id
	 * @return
	 */
	@Override
	public ArticleRes getArticleById(Long id) {

		String ip = BaseContext.getCurrentId();
		// 查询redis
		Map<Object, Object> hash = cacheUtil.getHashAll(CacheConstant.CACHE_ARTICLE_DETAIL + id);
		// 缓存命中
		if (hash != null && hash.size() > 1) {
			return BeanUtil.toBean(hash, ArticleRes.class);
		}

		ArticleRes articleRes = articleDao.getArticleById(id);
		// 异步存入redis
		CompletableFuture.runAsync(() -> {
			HashMap<String, String> map = new HashMap<>();
			BeanUtil.beanToMap(articleRes).forEach((key, value) -> map.put(key, value.toString()));
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

			// 更新文章卡片缓存
			applicationEventPub.publishEvent(new ArticleCardEvent(this, id));
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

			articleDao.update(Wrappers.lambdaUpdate(Article.class)
					.eq(Article::getId, id)
					.setSql("view_count = view_count + 1"));

			String hash1 = cacheUtil.getHash(CacheConstant.CACHE_ARTICLE_DETAIL + id, "viewCount");

			if (StrUtil.isNotBlank(hash1)) {
				cacheUtil.setHash(CacheConstant.CACHE_ARTICLE_DETAIL + id, "viewCount", String.valueOf(Integer.parseInt(hash1) + 1));
				cacheUtil.setStr(CacheConstant.CACHE_ARTICLE_VIEW_ID + id + ":" + ip, "1");

				// 更新卡片
				applicationEventPub.publishEvent(new ArticleCardEvent(this, id));
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
