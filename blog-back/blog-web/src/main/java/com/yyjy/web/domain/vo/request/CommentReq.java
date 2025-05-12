package com.yyjy.web.domain.vo.request;

import lombok.Data;

@Data
public class CommentReq {
	private Long articleId;
	private String nickName;
	private String content;
	private String ipAddress;

}
