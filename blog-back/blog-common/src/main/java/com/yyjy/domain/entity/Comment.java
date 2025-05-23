package com.yyjy.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author yyjy
 * @since 2025-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "文章id")
    @TableField("article_id")
    private Long articleId;

    @Schema(description = "评论内容")
    @TableField("content")
    private String content;

    @Schema(description = "昵称")
    @TableField("nick_name")
    private String nickName;

    @Schema(description = "ip属地")
    @TableField("ip_address")
    private String ipAddress;

    @Schema(description = "ip")
    @TableField("ip")
    private String ip;

    @Schema(description = "回复的昵称")
    @TableField("reply_nick_name")
    private String replyNickName;

    @Schema(description = "父评论id")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "根评论id")
    @TableField("root_parent_id")
    private Long rootParentId;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "是否为作者")
    @TableField("is_author")
    private Integer isAuthor;

    @Schema(description = "删除标志（0代表未删除，1代表已删除）")
    @TableField("del_flag")
    private Integer delFlag;
}
