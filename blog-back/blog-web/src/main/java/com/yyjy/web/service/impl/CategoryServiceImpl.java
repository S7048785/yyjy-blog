package com.yyjy.web.service.impl;

import cn.hutool.core.collection.CollUtil;
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

	@Override
	public List<CategoryRes> list() {
		List<Category> list = categoryDao.getAll();
		// 封装成Res
		return CollUtil.map(list, item -> new CategoryRes(item.getId(), item.getName()), true);
	}
}
