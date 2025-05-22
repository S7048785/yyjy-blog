package com.yyjy.service.impl;

import com.yyjy.properties.MinioConfig;
import com.yyjy.utils.MinioUtils;
import com.yyjy.dao.PhotoDao;
import com.yyjy.domain.entity.Photo;
import com.yyjy.mapper.PhotoMapper;
import com.yyjy.service.PhotoService;
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
