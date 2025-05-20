package com.yyjy.listener;

import cn.hutool.json.JSONUtil;
import com.yyjy.constant.CacheConstant;
import com.yyjy.dao.ArticleDao;
import com.yyjy.domain.vo.response.ArticleCardRes;
import com.yyjy.event.ArticleCardEvent;
import com.yyjy.utils.CacheUtil;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ArticleListener {

	private final CacheUtil cacheUtil;
	private final ArticleDao articleDao;

	public ArticleListener(CacheUtil cacheUtil, ArticleDao articleDao) {
		this.cacheUtil = cacheUtil;
		this.articleDao = articleDao;
	}

	@EventListener
	public void updateArticleCardList(ArticleCardEvent event) {
		while (true) {
			try {
				// 加锁
				boolean b = cacheUtil.tryLock(CacheConstant.CACHE_ARTICLE_LIST_LOCK);
				if (b) {
					// 更新缓存 卡片列表
					Long articleId = event.getArticleId();
					ArticleCardRes articleCard = articleDao.getArticleCardById(articleId);
					if (articleCard != null) {
						cacheUtil.updateSortedSetByScore(CacheConstant.CACHE_ARTICLE_LIST, JSONUtil.toJsonStr(articleCard), Double.parseDouble(articleCard.getCreateTime()));
					}
					return;
				} else {
					// 等待两秒
					Thread.sleep(2000);
					continue;
				}
			} catch (Exception e) {
				return;
			}
			finally {
				cacheUtil.unlock(CacheConstant.CACHE_ARTICLE_LIST_LOCK);
			}
		}

	}
}
