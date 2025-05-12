package com.yyjy.web.domain.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ArticleRes {
	@Schema(description = "文章id")
	private Long id;

	@Schema(description = "标题")
	private String title;

	@Schema(description = "内容")
	private String content;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "浏览量")
	private Long viewCount;

	@Schema(description = "点赞量")
	private Long likeCount;

	@Schema(description = "评论量")
	private Long commentCount;
}
