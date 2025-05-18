package com.yyjy.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.web.domain.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyjy.web.domain.vo.request.ArticlePageReq;
import com.yyjy.web.domain.vo.response.ArticleCardRes;
import com.yyjy.web.domain.vo.response.ArticleRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author yyjy
 * @since 2025-05-06
 */
public interface ArticleMapper extends BaseMapper<Article> {

	List<ArticleCardRes> list(long current, long size, ArticlePageReq req);

	Page<ArticleCardRes> page(Page<ArticleCardRes> page, ArticlePageReq req);

	ArticleRes getArticleById(Long id);
}
