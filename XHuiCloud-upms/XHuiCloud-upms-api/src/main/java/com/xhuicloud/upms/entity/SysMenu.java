package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单表
 */
@Data
@ApiModel(value = "菜单")
public class SysMenu implements Serializable {
    /**
     * 菜单id
     */
    @TableId(type = IdType.AUTO)
    private Integer menuId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 权限
     */
    private String permission;

    /**
     * 路径
     */
    private String path;


    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 图标
     */
    private String icon;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 0:否 1:是
     */
    private Integer isDel;

    /**
     * 父菜单ID
     */
    @NotNull(message = "菜单父ID不能为空")
    private Integer parentId;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 菜单类型:0菜单 1按钮
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    private static final long serialVersionUID = 1L;
}
