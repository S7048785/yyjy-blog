package com.yyjy.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.domain.entity.Photo;
import com.yyjy.mapper.PhotoMapper;
import org.springframework.stereotype.Service;

@Service
public class PhotoDao extends ServiceImpl<PhotoMapper, Photo>{
}
