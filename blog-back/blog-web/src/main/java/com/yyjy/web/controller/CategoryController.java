package com.yyjy.web.controller;

import com.yyjy.common.result.Result;
import com.yyjy.web.domain.vo.response.CategoryRes;
import com.yyjy.web.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "分类管理")
@RestController
@RequestMapping("/category")
public class CategoryController {

	@Resource
	private CategoryService categoryService;

	@Operation(summary = "获取分类列表")
	@GetMapping("/list")
	public Result<List<CategoryRes>> list() {
		return Result.ok(categoryService.list());
	}
}
