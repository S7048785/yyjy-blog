package com.yyjy.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yyjy.dao.CategoryDao;
import com.yyjy.domain.entity.Category;
import com.yyjy.domain.vo.response.CategoryRes;
import com.yyjy.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "分类管理")
@RestController
@RequestMapping("/admin/category")
public class CategoryController {

	@Resource
	private CategoryDao categoryDao;

	@Operation(summary = "获取分类列表")
	@GetMapping("/list")
	public Result<List<CategoryRes>> list() {
		List<Category> list = categoryDao.list(
				new LambdaQueryWrapper<Category>()
						.select(Category::getId, Category::getName)
						.eq(Category::getDelFlag, 0));
		List<CategoryRes> map = CollUtil.map(list, item -> BeanUtil.copyProperties(item, CategoryRes.class), true);
		return Result.ok(map);
	}
}
