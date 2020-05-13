package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zsinda.fdp.annotation.Excel;
import com.zsinda.fdp.enums.ColumnType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户表
 */
@Data
@TableName(value = "sys_user")
@ApiModel(value = "com-zsinda-fdp-entity-SysUser")
public class SysUser implements Serializable {
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value = "用户id")
    @Excel(name = "用户编号", cellType = ColumnType.NUMERIC)
    private Integer userId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "用户名")
    @Excel(name = "用户名称")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    @ApiModelProperty(value = "头像")
    @Excel(name = "用户头像")
    private String avatar;

    /**
     * 手机号码
     */
    @TableField(value = "phone")
    @ApiModelProperty(value = "手机号码")
    @Excel(name = "用户手机")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱")
    @Excel(name = "用户邮箱")
    private String email;

    /**
     * 性别:0 女、1  男、2  其他
     */
    @TableField(value = "sex")
    @ApiModelProperty(value = "性别:0 女、1  男、2  其他")
    @Excel(name = "用户性别", readConverterExp = "女=0,男=1,其他=2")
    private Integer sex;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "修改时间")
    @Excel(name = "更新时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 0:账号被锁
     */
    @TableField(value = "lock_flag")
    @ApiModelProperty(value = "0:账号被锁")
    @Excel(name = "锁定状态", readConverterExp = "已锁定=0,正常=1")
    private Integer lockFlag;

    /**
     * 0: 禁用 1：启用
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "0: 禁用 1：启用")
    @Excel(name = "注销状态", readConverterExp = "已注销=0,正常=1")
    private Integer delFlag;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    /**
     * 角色名称
     */
    @Excel(name = "用户角色")
    @TableField(exist = false)
    private List<Integer> roleIds;

    /**
     * 角色名称
     */
    @Excel(name = "用户部门")
    @TableField(exist = false)
    private List<Integer> deptIds;

    private static final long serialVersionUID = 1L;
}
