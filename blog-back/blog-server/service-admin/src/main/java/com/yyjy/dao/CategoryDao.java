package com.yyjy.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.domain.entity.Category;
import com.yyjy.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryDao extends ServiceImpl<CategoryMapper, Category> {
}
