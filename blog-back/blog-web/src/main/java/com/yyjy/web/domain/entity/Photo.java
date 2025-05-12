package com.yyjy.web.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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

    @Schema(description = "相册id")
    @TableField("album_id")
    private Integer albumId;

    @Schema(description = "照片名")
    @TableField("photo_name")
    private String photoName;

    @Schema(description = "照片描述")
    @TableField("photo_desc")
    private String photoDesc;

    @Schema(description = "照片链接")
    @TableField("photo_url")
    private String photoUrl;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
