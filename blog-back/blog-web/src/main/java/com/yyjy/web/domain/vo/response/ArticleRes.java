package com.yyjy.web.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ArticleRes {
	@Schema(description = "文章id")
	private Long id;

	@Schema(description = "标题")
	private String title;

	@Schema(description = "内容")
	private String content;

	@Schema(description = "创建时间")
	private String createTime;

	@Schema(description = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String updateTime;

	@Schema(description = "浏览量")
	private Long viewCount;

	@Schema(description = "点赞量")
	private Long likeCount;

	@Schema(description = "评论量")
	private Long commentCount;
}
