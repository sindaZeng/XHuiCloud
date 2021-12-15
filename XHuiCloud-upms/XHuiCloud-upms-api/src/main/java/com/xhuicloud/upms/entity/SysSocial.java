package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 社交表
 */
@Data
@ApiModel(value="社交")
public class SysSocial extends Model<SysSocial> {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 开放平台id
     */
    @TableField(value = "app_id")
    private String appId;

    /**
     * 开放平台密钥
     */
    @TableField(value = "`app_ secret`")
    private String appSecret;

    /**
     * 开放平台描述
     */
    @TableField(value = "app_desc")
    private String appDesc;

    /**
     * 重定向url
     */
    @TableField(value = "redirect_url")
    private String redirectUrl;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    /**
     * 创建者id
     */
    @ApiModelProperty(value = "创建者id", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private Integer createId;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", hidden = true)
    private LocalDateTime updateTime;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id", hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    private Integer updateId;

    /**
     * 0:否 1:是
     */
    @ApiModelProperty(value = "0:否 1:是")
    private Integer isDel;
}
