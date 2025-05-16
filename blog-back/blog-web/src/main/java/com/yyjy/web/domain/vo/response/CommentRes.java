package com.yyjy.web.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yyjy.web.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentRes {
	private Long id;
	private String nickName;
	private String content;
	private String ipAddress;
	private Integer isAuthor;
	private String createTime;

	/**
	 * 父评论id (顶部评论为null)
	 */
	private Long parentId;
	/**
	 * 根评论id (顶部评论为null)
	 */
	private Long rootParentId;
	/**
	 * 回复上层用户的昵称
	 */
	private String replyNickName;
	/**
	 * 子评论
	 */
	private List<CommentRes> children;
}
