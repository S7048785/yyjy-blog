package com.yyjy.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.domain.entity.Tag;
import com.yyjy.mapper.TagMapper;
import org.springframework.stereotype.Service;

@Service
public class TagDao extends ServiceImpl <TagMapper, Tag>{
}
