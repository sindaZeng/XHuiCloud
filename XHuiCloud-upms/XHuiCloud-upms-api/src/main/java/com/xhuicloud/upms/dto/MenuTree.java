/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.upms.dto;

import com.xhuicloud.upms.vo.MenuVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: XHuiCloud
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
     * 国际化key
     */
    private String internationalization;

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
    private Integer type;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 开启禁用
     */
    private Integer isDel;

    /**
     * 当是按钮时，不允许拥有下级菜单
     */
    private Boolean disabled;

    public MenuTree() {
    }

    public MenuTree(int id, String name, String internationalization, int parentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.internationalization = internationalization;
    }

    public MenuTree(int id, String name, String internationalization, MenuTree parent) {
        this.id = id;
        this.parentId = parent.getId();
        this.name = name;
        this.internationalization = internationalization;
    }

    public MenuTree(MenuVo menuVo) {
        this.id = menuVo.getId();
        this.parentId = menuVo.getParentId();
        this.icon = menuVo.getIcon();
        this.name = menuVo.getName();
        this.internationalization = menuVo.getInternationalization();
        this.path = menuVo.getPath();
        this.type = menuVo.getType();
        this.sort = menuVo.getSort();
    }


}
