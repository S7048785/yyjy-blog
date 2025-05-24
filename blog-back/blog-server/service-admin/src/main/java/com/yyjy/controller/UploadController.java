package com.yyjy.controller;

import com.yyjy.constant.UploadConstant;
import com.yyjy.exception.BaseException;
import com.yyjy.result.Result;
import com.yyjy.service.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "上传接口")
@RestController
@RequestMapping("/admin/upload")
public class UploadController {

	@Resource
	private UploadService uploadService;

	@Operation(summary = "上传图片")
	@PostMapping("/image")
	public String uploadImage(@RequestParam("file") MultipartFile file) {
		if (!"image/jpeg".equals(file.getContentType()) && !"image/png".equals(file.getContentType())) {
			throw new BaseException(UploadConstant.NOT_IMAGE_TYPE);
		}

		return uploadService.upload(file);
	}
}
