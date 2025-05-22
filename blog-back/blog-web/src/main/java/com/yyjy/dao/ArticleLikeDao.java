package com.yyjy.dao;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.context.BaseContext;
import com.yyjy.domain.entity.ArticleLike;
import com.yyjy.mapper.ArticleLikeMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeDao extends ServiceImpl <ArticleLikeMapper, ArticleLike>{

	public ArticleLike getOne(Long articleId, String userIp) {
		return getOne(Wrappers.lambdaQuery(ArticleLike.class)
				.eq(ArticleLike::getArticleId, articleId)
				.eq(ArticleLike::getUserIp, userIp));
	}

	public boolean exists(String ip, Long id) {
		return exists(Wrappers.lambdaQuery(ArticleLike.class)
				.eq(ArticleLike::getArticleId, id)
				.eq(ArticleLike::getUserIp, BaseContext.getCurrentId()));
	}
}
