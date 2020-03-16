package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sys_menu")
public class SysMenu implements Serializable {
    /**
     * 菜单id
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    /**
     * 菜单名称
     */
    @TableField(value = "name")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 权限
     */
    @TableField(value = "permission")
    private String permission;

    /**
     * 路径
     */
    @TableField(value = "path")
    private String path;


    /**
     * 重定向地址
     */
    @TableField(value = "redirect")
    private String redirect;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 0已删除
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

    /**
     * 父菜单ID
     */
    @TableField(value = "parent_id")
    @NotNull(message = "菜单父ID不能为空")
    private Integer parentId;

    /**
     * 排序值
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 菜单类型:0菜单 1按钮
     */
    @TableField(value = "type")
    @NotNull(message = "菜单类型不能为空")
    private Integer type;



    private static final long serialVersionUID = 1L;
}
