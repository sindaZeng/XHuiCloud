/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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