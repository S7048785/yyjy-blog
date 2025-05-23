package com.yyjy.dao;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.domain.entity.Category;
import com.yyjy.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryDao extends ServiceImpl <CategoryMapper, Category>{
	public List<Category> getAll() {
		return list(Wrappers.lambdaQuery(Category.class)
				.eq(Category::getStatus, "0"));
	}
}
