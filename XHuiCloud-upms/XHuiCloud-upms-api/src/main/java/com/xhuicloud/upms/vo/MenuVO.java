package com.xhuicloud.upms.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: XHuiCloud
 * @description: 菜单回显
 * @author: Sinda
 * @create: 2020-01-02 23:59
 */
@Data
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Integer id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单权限标识
     */
    private String permission;
    /**
     * 父菜单ID
     */
    private Integer parentId;
    /**
     * 图标
     */
    private String icon;
    /**
     * 前端路径
     */
    private String path;
    /**
     * 前端路径
     */
    private String redirect;
    /**
     * 排序值
     */
    private Integer sort;
    /**
     * 菜单类型 0菜单 1按钮
     */
    private Integer type;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 1：正常 0:删除
     */
    private Integer isDel;

}
