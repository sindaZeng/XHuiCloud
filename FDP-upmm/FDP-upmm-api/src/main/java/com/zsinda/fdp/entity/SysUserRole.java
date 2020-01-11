package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_user_role")
public class SysUserRole implements Serializable {
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private Integer userId;

    /**
     * 角色id
     */
    @TableId(value = "role_id", type = IdType.INPUT)
    private Integer roleId;

    private static final long serialVersionUID = 1L;
}