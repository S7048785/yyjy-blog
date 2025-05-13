package com.yyjy.web.domain.vo.request;

import lombok.Data;

@Data
public class CommentPageReq {
	private long articleId;
	private int current = 1;
	private int size = 5;
}
