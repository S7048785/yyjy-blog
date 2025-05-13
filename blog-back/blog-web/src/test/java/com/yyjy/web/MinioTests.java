package com.yyjy.web;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.yyjy.common.utils.MinioUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;

@SpringBootTest
public class MinioTests {

	@Autowired
	private MinioUtils minioUtils;

	@Test
	public void test1() {
		String bucket = "mediafiles";
		String fileName = "2025/03/24/36f97e29-4846-4ed6-920c-bc4ab2b86d25.jpg";

		InputStream file = minioUtils.getFile(bucket, fileName);

		FileUtil.writeFromStream(file, "D:\\abc.jpg");
	}
}
