package com.yyjy.controller;

import com.yyjy.result.Result;
import com.yyjy.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "图片上传")
@RequestMapping("/photo")
@RestController
public class PhotoController {

	@Autowired
	private PhotoService photoService;

	@Operation(summary = "上传图片")
	@PostMapping
	public Result<String> upload(@RequestBody MultipartFile file) {
		String filename = photoService.upload(file);
		return Result.ok(filename);
	}
}
