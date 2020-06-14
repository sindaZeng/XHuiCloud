package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 系统用户角色关联表
    */
@ApiModel(value="com-zsinda-fdp-entity-SysUserRole")
@Data
@TableName(value = "sys_user_role")
public class SysUserRole implements Serializable {
    @TableId(value = "sys_user_id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private String sysUserId;

    @TableId(value = "sys_role_id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private String sysRoleId;

    private static final long serialVersionUID = 1L;
}