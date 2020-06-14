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
    * 系统用户表
    */
@ApiModel(value="com-zsinda-fdp-entity-SysUser")
@Data
@TableName(value = "sys_user")
public class SysUser implements Serializable {
    @TableId(value = "id", type = IdType.UUID)
    @ApiModelProperty(value="")
    private String id;

    /**
     * 账号
     */
    @TableField(value = "account")
    @ApiModelProperty(value="账号")
    private String account;

    /**
     * 密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value="密码")
    private String password;

    /**
     * 盐
     */
    @TableField(value = "salt")
    @ApiModelProperty(value="盐")
    private String salt;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    @ApiModelProperty(value="昵称")
    private String nickname;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value="邮箱")
    private String email;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value="手机号")
    private String mobile;

    /**
     * 头像
     */
    @TableField(value = "head_icon")
    @ApiModelProperty(value="头像")
    private String headIcon;

    /**
     * 最后登录时间
     */
    @TableField(value = "last_login_time")
    @ApiModelProperty(value="最后登录时间")
    private Date lastLoginTime;

    /**
     * 最后登录ip
     */
    @TableField(value = "last_login_ip")
    @ApiModelProperty(value="最后登录ip")
    private String lastLoginIp;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value="创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value="更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态")
    private Short status;

    /**
     * 所属单位/机构/部门的ID（根据业务系统来定）
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value="所属单位/机构/部门的ID（根据业务系统来定）")
    private String deptId;

    private static final long serialVersionUID = 1L;
}
