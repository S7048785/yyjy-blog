package com.yyjy.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class ArticleColRes {
	private Long id;
	private String title;
	private String category;
	private String tags;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String date;
	private String status;
}
