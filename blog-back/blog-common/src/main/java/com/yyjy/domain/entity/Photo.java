package com.yyjy.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yyjy
 * @since 2025-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("photo")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "照片id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "照片链接")
    private String path;

    @Schema(description = "创建时间")
    @TableField(value = "create_time" ,fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
