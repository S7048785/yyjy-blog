package com.yyjy.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.common.constant.CacheConstant;
import com.yyjy.common.context.BaseContext;
import com.yyjy.common.utils.CacheUtil;
import com.yyjy.web.dao.ArticleDao;
import com.yyjy.web.domain.entity.Article;
import com.yyjy.web.domain.vo.request.ArticlePageReq;
import com.yyjy.web.domain.vo.response.ArticleCardRes;
import com.yyjy.web.domain.vo.response.ArticleHotRes;
import com.yyjy.web.domain.vo.response.ArticleHotRes;
import com.yyjy.web.domain.vo.response.ArticleRes;
import com.yyjy.web.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Resource
	private ArticleDao articleDao;

	@Resource
	private CacheUtil cacheUtil;

	@Override
	public List<ArticleHotRes> hotArticleList() {
		// 查询redis
//		Collection<String> sortedSet = cacheUtil.getSortedSet(CacheConstant.CACHE_ARTICLE_HOT_LIST, 1, 10);
//		if (CollUtil.isNotEmpty(sortedSet)) {
//			return JSONUtil.toList(sortedSet.toString(), ArticleHotRes.class);
//		}

		Page<Article> articlePage = new Page<>(1, 10);
		articlePage = articleDao.hotList(articlePage);
		List<Article> records = articlePage.getRecords();
		// 存入redis
		List<ArticleHotRes> articleHotRes = BeanUtil.copyToList(records, ArticleHotRes.class);
//		Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();

//		articleHotRes.forEach(item -> set.add(new DefaultTypedTuple<>(JSONUtil.toJsonStr(item), item.getViewCount().doubleValue())));
//		cacheUtil.setSortedSet(CacheConstant.CACHE_ARTICLE_HOT_LIST, set);
		return articleHotRes;
	}

	@Override
	public Page<ArticleCardRes> pageArticleList(ArticlePageReq req) {

		Page<ArticleCardRes> articleCardResPage = new Page<>(req.getCurrent(), req.getSize());
		articleCardResPage = articleDao.page(articleCardResPage, req);
		return articleCardResPage;
	}

	@Override
	public ArticleRes getArticleById(Long id) {
		String ip = BaseContext.getCurrentId();
		return articleDao.getArticleById(ip, id);
	}

	@Override
	public void likeArticle(Long id) {

	}
}
