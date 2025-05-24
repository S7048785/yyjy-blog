package com.yyjy.service;

import com.yyjy.domain.vo.request.ArticleDetailReq;
import com.yyjy.domain.vo.request.ArticleListReq;
import com.yyjy.domain.vo.response.ArticleColRes;
import com.yyjy.domain.vo.response.ArticleDetailRes;

import java.util.List;

public interface ArticleService {
	List<ArticleColRes> list(ArticleListReq req);

	Boolean removeById(Long id);

	Boolean removeBatchByIds(List<Long> ids);

	void save(ArticleDetailReq req);

	ArticleDetailRes getById(Long id);

	void updateById(ArticleDetailReq req);
}
