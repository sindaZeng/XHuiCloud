package com.zsinda.fdp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: FDPlatform
 * @description: UserDto
 * @author: Sinda
 * @create: 2020-03-17 15:40
 */
@Data
@ApiModel(value = "用户列表条件")
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "搜索内容: 用户名/手机号/邮箱")
    private String text;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "0:禁用 1:启用 2:全部")
    private Integer delFlag;

    @ApiModelProperty(value = "日期")
    private Date date;


}
