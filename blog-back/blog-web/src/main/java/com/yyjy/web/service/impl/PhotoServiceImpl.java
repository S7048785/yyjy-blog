package com.yyjy.web.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjy.common.properties.MinioConfig;
import com.yyjy.common.utils.MinioUtils;
import com.yyjy.web.dao.PhotoDao;
import com.yyjy.web.domain.entity.Photo;
import com.yyjy.web.mapper.PhotoMapper;
import com.yyjy.web.service.PhotoService;
import io.minio.MinioProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class PhotoServiceImpl implements PhotoService    {
	@Autowired
	private PhotoDao photoDao;
	@Autowired
	private MinioUtils minioUtils;
	@Autowired
	private MinioConfig minioConfig;
	@Override
	public String upload(MultipartFile file) {
		String s = minioUtils.uploadFile(minioConfig.getBucket(), file);
		// 存入数据库
		Photo photo = new Photo().setPath(s).setCreateTime(LocalDateTime.now());
		photoDao.save(photo);
		return minioConfig.getEndpoint()  + "/" + s;
	}
}
