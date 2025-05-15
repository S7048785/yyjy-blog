package com.yyjy.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yyjy.common.constant.CacheConstant;
import com.yyjy.common.utils.CacheUtil;
import com.yyjy.web.dao.CategoryDao;
import com.yyjy.web.domain.entity.Category;
import com.yyjy.web.domain.vo.response.CategoryRes;
import com.yyjy.web.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Resource
	private CategoryDao categoryDao;
	@Resource
	private CacheUtil cacheUtil;

	@Override
	public List<CategoryRes> list() {
		// 查询redis
		String str = cacheUtil.getStr(CacheConstant.CACHE_CATEGORY);
		if (StrUtil.isNotBlank(str)) {
			return JSONUtil.toList(str, CategoryRes.class);
		}
		List<Category> list = categoryDao.getAll();
		List<CategoryRes> categoryRes = BeanUtil.copyToList(list, CategoryRes.class);
		// 存入redis
		cacheUtil.setStr(CacheConstant.CACHE_CATEGORY, JSONUtil.toJsonStr(categoryRes));
		// 封装成Res
		return categoryRes;
	}
}
