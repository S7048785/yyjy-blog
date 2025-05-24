package com.yyjy.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	String upload(MultipartFile file);
}
