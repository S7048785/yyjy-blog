package com.yyjy.web.domain.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentReq {
	private Long articleId;
	private String nickName;
	private String content;
	private String replyNickName;
	private Long parentId;
	private Long rootParentId;
}
