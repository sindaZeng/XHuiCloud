package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="用户社交")
public class SysUserSocial extends Model<SysUserSocial> {
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

}
