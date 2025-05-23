package com.yyjy.domain.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "分类响应对象")
@AllArgsConstructor
public class CategoryRes {
	@Schema(description = "分类id")
	private Long id;
	@Schema(description = "分类名")
	private String name;
}
