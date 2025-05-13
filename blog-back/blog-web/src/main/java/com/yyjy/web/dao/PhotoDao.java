package com.yyjy.web.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.web.domain.entity.Photo;
import com.yyjy.web.mapper.PhotoMapper;
import org.springframework.stereotype.Service;

@Service
public class PhotoDao extends ServiceImpl<PhotoMapper, Photo>{
}
