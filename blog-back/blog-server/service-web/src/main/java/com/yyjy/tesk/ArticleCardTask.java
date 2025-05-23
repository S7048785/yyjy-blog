package com.yyjy.tesk;

import com.yyjy.utils.CacheUtil;
import com.yyjy.dao.ArticleDao;
import com.yyjy.domain.entity.Article;
import com.yyjy.service.ArticleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


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
