package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件表
 */
@Data
@TableName(value = "sys_file")
@ApiModel(value = "com-zsinda-fdp-entity-SysFile")
public class SysFile implements Serializable {
    /**
     * 文件ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "文件ID")
    private Integer id;

    /**
     * 图片Url
     */
    @TableField(value = "url")
    @ApiModelProperty(value = "图片Url")
    private String url;

    /**
     * 文件名称
     */
    @TableField(value = "file_name")
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /**
     * 文件大小
     */
    @TableField(value = "file_size")
    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    /**
     * 文件类型
     */
    @TableField(value = "file_type")
    @ApiModelProperty(value = "文件类型")
    private String fileType;

    /**
     * 桶名称
     */
    @TableField(value = "bucket_name")
    @ApiModelProperty(value = "桶名称")
    private String bucketName;

    /**
     * 上传用户id
     */
    @TableField(value = "create_id")
    @ApiModelProperty(value = "上传用户id")
    private Integer createId;

    /**
     * 上传时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "上传时间")
    private Date createTime;

    /**
     * 更新用户id
     */
    @TableField(value = "update_id")
    @ApiModelProperty(value = "更新用户id")
    private Integer updateId;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 0: 禁用 1：启用
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "0: 禁用 1：启用")
    private Integer delFlag;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    private static final long serialVersionUID = 1L;
}
