package com.yyjy.web.tesk;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.common.constant.CacheConstant;
import com.yyjy.common.utils.CacheUtil;
import com.yyjy.web.dao.ArticleDao;
import com.yyjy.web.domain.entity.Article;
import com.yyjy.web.domain.vo.request.ArticlePageReq;
import com.yyjy.web.domain.vo.response.ArticleCardRes;
import com.yyjy.web.service.ArticleService;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;


@Component
public class ArticleCardTask {
	private final ArticleService articleService;
	private final CacheUtil cacheUtil;
	private final ArticleDao articleDao;

	public ArticleCardTask(ArticleService articleService, CacheUtil cacheUtil, ArticleDao articleDao) {
		this.articleService = articleService;
		this.cacheUtil = cacheUtil;
		this.articleDao = articleDao;
	}

	/**
	 * 更新文章卡片缓存
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void execute() {
		while  (true) {
			// 加锁
			boolean b = cacheUtil.tryLock(CacheConstant.CACHE_ARTICLE_LOCK);
			if (b) {
				try {
					Page<ArticleCardRes> articleCardResPage = new Page<>(1, 5);
					ArticlePageReq articlePageReq = new ArticlePageReq(null, 1, 5, System.currentTimeMillis() / 1000);
					Page<ArticleCardRes> res = articleDao.page(articleCardResPage, articlePageReq);
					// 存入redis
					if (CollUtil.isNotEmpty(articleCardResPage.getRecords())) {
						// 异步存入redis
						CompletableFuture.runAsync(() -> {
							Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
							// 存入redis
							res.getRecords().forEach(item -> {
								DefaultTypedTuple<String> articleCardResDefaultTypedTuple = new DefaultTypedTuple<>(JSONUtil.toJsonStr(item), Double.parseDouble(item.getCreateTime()));
								set.add(articleCardResDefaultTypedTuple);
							});
							cacheUtil.setSortedSetByScore(CacheConstant.CACHE_ARTICLE_LIST, set);
						});
					}
					return;
				} finally  {
					cacheUtil.unlock(CacheConstant.CACHE_ARTICLE_LOCK);
				}
			}
		}
	}
}
