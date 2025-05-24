package com.yyjy.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "文章列表请求参数")
@Data
@NoArgsConstructor
public class ArticleListReq {
	@Schema(description = "文章id")
	private Long id;
	@Schema(description = "文章标题")
	private String title;
	@Schema(description = "文章标签")
	private List<String> tags;
	@Schema(description = "文章分类")
	private Long category;
	@Schema(description = "文章状态")
	private String status;

	@Schema(description = "文章开始时间")
	private LocalDate begin;

	@Schema(description = "文章结束时间")
	private LocalDate end = LocalDate.now().plusDays(1);
}
