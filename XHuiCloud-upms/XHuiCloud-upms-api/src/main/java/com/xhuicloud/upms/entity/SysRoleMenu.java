package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 角色菜单表
 */
@Data
@ApiModel(value = "角色菜单")
public class SysRoleMenu extends Model<SysRoleMenu> {
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 菜单id
     */
    private Integer menuId;

}