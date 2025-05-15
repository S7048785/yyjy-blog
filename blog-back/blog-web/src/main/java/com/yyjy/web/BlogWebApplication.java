package com.yyjy.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.yyjy.web.mapper")
@SpringBootApplication(scanBasePackages = {"com.yyjy.web", "com.yyjy.common"})
public class BlogWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogWebApplication.class, args);
	}

}
