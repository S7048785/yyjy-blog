package com.yyjy.service;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
	String upload(MultipartFile file);
}
