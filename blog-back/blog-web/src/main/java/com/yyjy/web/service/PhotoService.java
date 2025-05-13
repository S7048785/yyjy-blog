package com.yyjy.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyjy.web.domain.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
	String upload(MultipartFile file);
}
