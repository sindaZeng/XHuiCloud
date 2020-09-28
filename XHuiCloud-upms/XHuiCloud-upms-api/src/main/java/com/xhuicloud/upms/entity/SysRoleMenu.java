package com.xhuicloud.upms.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色菜单表
 */
@Data
@ApiModel(value = "角色菜单")
public class SysRoleMenu implements Serializable {
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 菜单id
     */
    private Integer menuId;

    private static final long serialVersionUID = 1L;
}