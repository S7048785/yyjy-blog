package com.yyjy.domain.vo.request;

import lombok.Data;

@Data
public class CommentPageReq {
	private long articleId;
	private long current = 1L;
	private long size = 5L;
}
