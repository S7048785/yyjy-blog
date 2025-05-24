package com.yyjy.domain.vo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ArticleDetailReq {
	private Long id;
	@NotBlank(message = "标题不能为空")
	private String title;
	@NotBlank(message = "内容不能为空")
	private String content;
	private String summary;
	private String status;
	private List<String> tags;
	private Long categoryId;
	private String thumbnail;
}
