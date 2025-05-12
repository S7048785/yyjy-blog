package com.yyjy.web.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentRes {
	private Long id;
	private String nickName;
	private String content;
	private String ipAddress;
	private Integer isAuthor;
	private String createTime;
}
