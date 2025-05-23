package com.yyjy.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author YYJYP
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="文章点赞关系表")
@TableName("article_like")
public class ArticleLike {
	@Schema(description="主键id")
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	@Schema(description="文章id")
	private Long articleId;
	@Schema(description="用户ip")
	private String userIp;
	@Schema(description="点赞时间")
	private Integer delFlag;
}
