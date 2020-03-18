package com.zsinda.fdp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: FDPlatform
 * @description: UserVo
 * @author: Sinda
 * @create: 2020-03-17 15:47
 */
@Data
@ApiModel(value = "用户列表")
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "主键")
    private Integer userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    /**
     * 0:账号被锁
     */
    @ApiModelProperty(value = "0:账号被锁")
    private Integer lockFlag;

    /**
     * 0:账号已注销
     */
    @ApiModelProperty(value = "0:账号已注销")
    private Integer delFlag;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private List<String> roleName;


}
