package com.xhuicloud.upms.vo;

import com.xhuicloud.common.core.annotation.Scalpel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/10/20
 */
@Data
@ApiModel(value = "客户端列表")
public class ClientVo implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "客户端名称")
    private String name;

    @ApiModelProperty(value = "客户端id")
    private String clientId;

    @ApiModelProperty(value = "客户端资源id")
    private String resourceIds;

    @ApiModelProperty(value = "客户端密钥")
    @Scalpel(before = 0, after = 0)
    private String clientSecret;

    @ApiModelProperty(value = "权限范围")
    private String scope;

    @ApiModelProperty(value = "授权类型")
    private String[] authorizedGrantTypes;

    @ApiModelProperty(value = "重定向地址")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "客户端权限")
    private String authorities;

    @ApiModelProperty(value = "access_token的有效时间值(单位:秒)")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "refresh_token的有效时间值(单位:秒)")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "登录验证码0: 否 1：是")
    private Integer captchaEnable;

    @ApiModelProperty(value = "重复登录0: 否 1：是")
    private Integer multiLogin;

    @ApiModelProperty(value = "自动授权")
    private String autoApprove;

    @ApiModelProperty(value = "令牌模式")
    private String tokenFormat;

    @ApiModelProperty(value = "0: 否 1：是")
    private Integer isDel;

    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    private static final long serialVersionUID = 1L;

}
