package com.yyjy.web.domain.vo.request;

import lombok.Data;

@Data
public class CommentPageReq {
	private Long articleId;
	private Integer current;
	private Integer size;
}
