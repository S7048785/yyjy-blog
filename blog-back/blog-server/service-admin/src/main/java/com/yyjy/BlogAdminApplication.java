package com.yyjy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yyjy.mapper")
@SpringBootApplication
public class BlogAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogAdminApplication.class, args);
	}
}