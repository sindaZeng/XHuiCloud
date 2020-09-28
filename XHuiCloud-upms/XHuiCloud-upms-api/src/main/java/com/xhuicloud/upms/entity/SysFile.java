package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件表
 */
@Data
@ApiModel(value = "文件")
public class SysFile implements Serializable {
    /**
     * 文件ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "文件ID")
    private Integer id;

    /**
     * 图片Url
     */
    @ApiModelProperty(value = "图片Url")
    private String url;

    /**
     * 桶内文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /**
     * 原文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String name;

    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String fileType;

    /**
     * 桶名称
     */
    @ApiModelProperty(value = "桶名称")
    private String bucketName;

    /**
     * 上传用户id
     */
    @ApiModelProperty(value = "上传用户id")
    private Integer createId;

    /**
     * 上传时间
     */
    @ApiModelProperty(value = "上传时间")
    private LocalDateTime createTime;

    /**
     * 0:否 1:是
     */
    @ApiModelProperty(value="0:否 1:是")
    private Integer isDel;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    private static final long serialVersionUID = 1L;
}
