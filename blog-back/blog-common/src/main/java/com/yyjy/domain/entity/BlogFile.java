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
 * 
 * </p>
 *
 * @author yyjy
 * @since 2025-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("blog_file")
public class BlogFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "文件id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "文件url")
    @TableField("file_url")
    private String fileUrl;

    @Schema(description = "文件名")
    @TableField("file_name")
    private String fileName;

    @Schema(description = "文件大小")
    @TableField("file_size")
    private Integer fileSize;

    @Schema(description = "文件类型")
    @TableField("extend_name")
    private String extendName;

    @Schema(description = "文件路径")
    @TableField("file_path")
    private String filePath;

    @Schema(description = "是否为目录 (0否 1是)")
    @TableField("is_dir")
    private Boolean isDir;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private String createTime;

    @Schema(description = "更新时间")
    @TableField("update_time")
    private String updateTime;


}
