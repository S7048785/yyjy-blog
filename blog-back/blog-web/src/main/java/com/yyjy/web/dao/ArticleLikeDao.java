package com.yyjy.web.dao;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.web.domain.entity.ArticleLike;
import com.yyjy.web.mapper.ArticleLikeMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeDao extends ServiceImpl <ArticleLikeMapper, ArticleLike>{

	public ArticleLike getOne(Long articleId, String userIp) {
		return getOne(Wrappers.lambdaQuery(ArticleLike.class)
				.eq(ArticleLike::getArticleId, articleId)
				.eq(ArticleLike::getUserIp, userIp));
	}
}
