package com.yyjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.domain.entity.Article;
import com.yyjy.domain.vo.request.ArticleListReq;
import com.yyjy.domain.vo.response.ArticleColRes;
import com.yyjy.domain.vo.response.ArticleDetailRes;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

	List<ArticleColRes> articleColPage(ArticleListReq req);

	ArticleDetailRes getArticleDetailById(Long id);
}
