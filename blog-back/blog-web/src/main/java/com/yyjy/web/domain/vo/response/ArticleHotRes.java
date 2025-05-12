package com.yyjy.web.domain.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleHotRes {
	@Schema(description = "文章id")
	private Long id;
	@Schema(description = "标题")
	private String title;
	@Schema(description = "浏览量")
	private Long viewCount;
}
