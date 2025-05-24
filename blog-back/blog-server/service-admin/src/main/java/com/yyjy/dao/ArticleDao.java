package com.yyjy.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.domain.entity.Article;
import com.yyjy.domain.vo.request.ArticleListReq;
import com.yyjy.domain.vo.response.ArticleColRes;
import com.yyjy.domain.vo.response.ArticleDetailRes;
import com.yyjy.mapper.ArticleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleDao extends ServiceImpl <ArticleMapper, Article>{

	@Resource
	private ArticleMapper articleMapper;

	public List<ArticleColRes> list(ArticleListReq req) {
		return articleMapper.articleColPage(req);
	}

	public ArticleDetailRes getById(Long id) {
		return articleMapper.getArticleDetailById(id);
	}
}
