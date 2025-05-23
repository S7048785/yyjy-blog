package com.yyjy.domain.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "文章分页请求对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageReq {
	@Schema(description = "分类id")
	private Long categoryId;
	@Schema(description = "当前页码")
	private Integer current = 1;
	@Schema(description = "每页数量")
	private Integer size = 5;
	@Schema(description = "最大时间戳")
	private Long max = System.currentTimeMillis() / 1000;
}
