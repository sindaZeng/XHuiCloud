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

package com.xhuicloud.upms.utils;

import cn.hutool.core.bean.BeanUtil;
import com.xhuicloud.upms.dto.DeptTree;
import com.xhuicloud.upms.dto.MenuTree;
import com.xhuicloud.upms.dto.TreeNode;
import com.xhuicloud.upms.entity.SysDept;
import com.xhuicloud.upms.entity.SysMenu;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: XHuiCloud
 * @description: TreeUtil
 * @author: Sinda
 * @create: 2020-01-02 23:32
 */
@UtilityClass
public class TreeUtil {

    /**
     * 构建菜单树
     *
     *
     * @param disabled
     * @param menus
     * @param root
     * @return
     */
    public List<MenuTree> buildMenuTree(Boolean disabled, List<SysMenu> menus, Long root) {
        List<MenuTree> trees = new ArrayList<>();
        MenuTree node;
        for (SysMenu menu : menus) {
            node = new MenuTree();
            node.setId(menu.getId());
            node.setParentId(menu.getParentId());
            node.setValue(menu.getId());
            node.setName(menu.getName());
            node.setInternationalization(menu.getInternationalization());
            node.setLabel(menu.getName());
            node.setPath(menu.getPath());
            node.setPermission(menu.getPermission());
            node.setIcon(menu.getIcon());
            node.setSort(menu.getSort());
            node.setType(menu.getType());
            node.setIsDel(menu.getIsDel());
            if (disabled)
                node.setDisabled(menu.getType() == 1);
            trees.add(node);
        }
        return TreeUtil.build(trees, root);
    }

    /**
     * 构建部门树
     *
     * @param depts
     * @param root
     * @return
     */
    public List<DeptTree> buildDeptTree(List<SysDept> depts, Long root) {
        List<DeptTree> deptTrees = depts.stream()
                .filter(dept -> !dept.getId().equals(dept.getParentId()))
                .sorted(Comparator.comparingInt(SysDept::getSort))
                .map(dept -> {
                    DeptTree node = new DeptTree();
                    BeanUtil.copyProperties(dept, node);
                    node.setId(dept.getId());
                    node.setValue(dept.getId());
                    node.setLabel(dept.getName());
                    return node;
                }).collect(Collectors.toList());
        return TreeUtil.build(deptTrees, root);
    }

    public <T extends TreeNode> List<T> build(List<T> treeNodes, Long root) {
        List<T> trees = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }
            for (T it : treeNodes) {
                if (it.getParentId() == treeNode.getId()) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

}
