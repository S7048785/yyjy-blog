package com.yyjy.web.domain.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
public class ArticleCardRes {
	@Schema(description = "文章id")
	private Long id;

	@Schema(description = "标题")
	private String title;

	@Schema(description = "摘要")
	private String summary;

	@Schema(description = "封面地址")
	private String thumbnail;

	@Schema(description = "创建时间")
	private String createTime;

	@Schema(description = "浏览量")
	private Long viewCount;

	@Schema(description = "点赞量")
	private Long likeCount;

	@Schema(description = "标签")
	private String tags;
}
