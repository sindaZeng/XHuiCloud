package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.*;

/**
 * 终端信息
 */
@Data
@ApiModel(value = "终端信息")
public class SysClientDetails implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;

    @TableField(value = "client_id")
    @ApiModelProperty(value = "")
    private String clientId;

    @TableField(value = "resource_ids")
    @ApiModelProperty(value = "")
    private String resourceIds;

    @TableField(value = "client_secret")
    @ApiModelProperty(value = "")
    private String clientSecret;

    @TableField(value = "`scope`")
    @ApiModelProperty(value = "")
    private String scope;

    @TableField(value = "authorized_grant_types")
    @ApiModelProperty(value = "")
    private String authorizedGrantTypes;

    @TableField(value = "web_server_redirect_uri")
    @ApiModelProperty(value = "")
    private String webServerRedirectUri;

    @TableField(value = "authorities")
    @ApiModelProperty(value = "")
    private String authorities;

    @TableField(value = "access_token_validity")
    @ApiModelProperty(value = "")
    private Integer accessTokenValidity;

    @TableField(value = "refresh_token_validity")
    @ApiModelProperty(value = "")
    private Integer refreshTokenValidity;

    @TableField(value = "additional_information")
    @ApiModelProperty(value = "")
    private String additionalInformation;

    @TableField(value = "autoapprove")
    @ApiModelProperty(value = "")
    private String autoapprove;

    /**
     * 0: 否 1：是
     */
    @TableField(value = "is_del")
    @ApiModelProperty(value = "0: 否 1：是")
    private Integer isDel;

    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "")
    private Integer tenantId;

    private static final long serialVersionUID = 1L;

}