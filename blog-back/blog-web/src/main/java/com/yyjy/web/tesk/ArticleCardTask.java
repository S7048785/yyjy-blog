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
	@Scheduled(cron = "0 0/5 * * * ?")
	public void execute() {

	}
}
