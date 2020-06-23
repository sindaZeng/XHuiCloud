package com.zsinda.fdp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: FDPlatform
 * @description: FileVo
 * @author: Sinda
 * @create: 2020-06-01 17:46
 */
@Data
public class FileVo implements Serializable {

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
     * 上传用户
     */
    @ApiModelProperty(value = "上传用户")
    private String createName;

    /**
     * 上传时间
     */
    @ApiModelProperty(value = "上传时间")
    private LocalDateTime createTime;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "所属租户")
    private String tenantName;

    private static final long serialVersionUID = 1L;
}
