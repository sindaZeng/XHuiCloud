package com.zsinda.fdp.dto;

import com.zsinda.fdp.vo.MenuVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: FDPlatform
 * @description: MenuTree
 * @author: Sinda
 * @create: 2020-01-02 23:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends TreeNode {

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 权限
     */
    private String permission;

    /**
     * 前端路径
     */
    private String path;

    /**
     * 菜单类型0菜单 1按钮
     */
    private String type;

    /**
     * 排序值
     */
    private Integer sort;

    public MenuTree() {
    }

    public MenuTree(int id, String name, int parentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public MenuTree(int id, String name, MenuTree parent) {
        this.id = id;
        this.parentId = parent.getId();
        this.name = name;
    }

    public MenuTree(MenuVO menuVo) {
        this.id = menuVo.getMenuId();
        this.parentId = menuVo.getParentId();
        this.icon = menuVo.getIcon();
        this.name = menuVo.getName();
        this.path = menuVo.getPath();
        this.type = menuVo.getType();
        this.sort = menuVo.getSort();
    }


}
