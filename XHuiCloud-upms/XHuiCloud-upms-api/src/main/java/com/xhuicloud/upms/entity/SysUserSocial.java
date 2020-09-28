package com.xhuicloud.upms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="用户社交")
public class SysUserSocial implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value="用户Id")
    private Integer userId;

    /**
     * 开放平台表id
     */
    @ApiModelProperty(value="开放平台表id")
    private String socialType;

    /**
     * 用户openid
     */
    @ApiModelProperty(value="用户openid")
    private String userOpenid;

    private static final long serialVersionUID = 1L;
}
