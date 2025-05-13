package com.yyjy.web.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author yyjy
 * @since 2025-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "分类名")
    @TableField("name")
    private String name;

    @Schema(description = "状态0:正常,1禁用")
    @TableField("status")
    private String status;

    @TableField(condition = "create_time", fill = FieldFill.INSERT)
    private String createTime;

    @Schema(description = "删除标志（0代表未删除，1代表已删除）")
    @TableField("del_flag")
    private Integer delFlag;


}
