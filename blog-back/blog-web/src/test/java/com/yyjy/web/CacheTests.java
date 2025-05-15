package com.yyjy.web;

import com.yyjy.common.utils.CacheUtil;
import com.yyjy.web.domain.vo.response.CategoryRes;
import com.yyjy.web.service.CategoryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class CacheTests {

	@Resource
	private CategoryService categoryService;

	@Test
	public void testCache() {
		List<CategoryRes> list = categoryService.list();

		log.info("list: {}", list);
	}
}
