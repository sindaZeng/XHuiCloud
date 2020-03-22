package com.zsinda.fdp.utils;

import com.zsinda.fdp.dto.DeptTree;
import com.zsinda.fdp.dto.MenuTree;
import com.zsinda.fdp.dto.TreeNode;
import com.zsinda.fdp.entity.SysDept;
import com.zsinda.fdp.entity.SysMenu;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: FDPlatform
 * @description: TreeUtil
 * @author: Sinda
 * @create: 2020-01-02 23:32
 */
@UtilityClass
public class TreeUtil {

    /**
     * 构建菜单树
     * @param menus
     * @param root
     * @return
     */
    public List<MenuTree> buildMenuTree(List<SysMenu> menus, int root) {
        List<MenuTree> trees = new ArrayList<>();
        MenuTree node;
        for (SysMenu menu : menus) {
            node = new MenuTree();
            node.setId(menu.getMenuId());
            node.setParentId(menu.getParentId());
            node.setValue(menu.getMenuId());
            node.setName(menu.getName());
            node.setLabel(menu.getName());
            node.setPath(menu.getPath());
            node.setPermission(menu.getPermission());
            node.setIcon(menu.getIcon());
            node.setSort(menu.getSort());
            node.setType(menu.getType());
            node.setDelFlag(menu.getDelFlag());
            trees.add(node);
        }
        return TreeUtil.build(trees, root);
    }

    /**
     * 构建部门树
     * @param depts
     * @param root
     * @return
     */
    public List<DeptTree> buildDeptTree(List<SysDept> depts, int root) {
        List<DeptTree> deptTrees = depts.stream()
                .filter(dept -> !dept.getDeptId().equals(dept.getParentId()))
                .sorted(Comparator.comparingInt(SysDept::getSort))
                .map(dept -> {
                    DeptTree node = new DeptTree();
                    node.setId(dept.getDeptId());
                    node.setParentId(dept.getParentId());
                    node.setAddress(dept.getAddress());
                    node.setName(dept.getName());
                    return node;
                }).collect(Collectors.toList());
        return TreeUtil.build(deptTrees, root);
    }

    public <T extends TreeNode> List<T> build(List<T> treeNodes, Object root) {
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
