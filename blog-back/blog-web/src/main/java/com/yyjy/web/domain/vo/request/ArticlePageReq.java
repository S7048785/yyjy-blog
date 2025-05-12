package com.yyjy.web.domain.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "文章分页请求对象")
@Data
public class ArticlePageReq {
	@Schema(description = "分类id")
	private Long categoryId;
	@Schema(description = "当前页码")
	private Integer current = 1;
	@Schema(description = "每页数量")
	private Integer size = 10;
}
