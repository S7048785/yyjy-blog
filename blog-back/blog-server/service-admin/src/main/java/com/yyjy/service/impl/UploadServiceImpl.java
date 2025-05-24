package com.yyjy.service.impl;

import com.yyjy.dao.PhotoDao;
import com.yyjy.domain.entity.Photo;
import com.yyjy.properties.MinioConfig;
import com.yyjy.service.UploadService;
import com.yyjy.utils.MinioUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements UploadService {

	@Resource
	private MinioUtils minioUtils;
	@Resource
	private MinioConfig minioConfig;
	@Autowired
	private PhotoDao photoDao;


	@Override
	public String upload(MultipartFile file) {
		String path = minioUtils.uploadFile(minioConfig.getBucket(), file);

		photoDao.save(new Photo().setPath(path));

		return minioConfig.getEndpoint() + "/" + path;
	}
}
