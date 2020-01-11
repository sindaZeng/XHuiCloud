package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_role_menu")
public class SysRoleMenu implements Serializable {
    /**
     * 角色id
     */
    @TableId(value = "role_id", type = IdType.INPUT)
    private Integer roleId;

    /**
     * 菜单id
     */
    @TableId(value = "menu_id", type = IdType.INPUT)
    private Integer menuId;

    private static final long serialVersionUID = 1L;
}