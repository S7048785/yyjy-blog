package com.yyjy.domain.vo.response;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleDetailRes {
	private Long id;
	private String title;
	private String content;
	private String summary;
	private Long categoryId;
	private String thumbnail;
	private String status;
	private String tags;
}
