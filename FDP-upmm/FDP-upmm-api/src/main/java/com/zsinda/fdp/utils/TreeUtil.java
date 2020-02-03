package com.zsinda.fdp.utils;

import com.zsinda.fdp.dto.MenuTree;
import com.zsinda.fdp.dto.TreeNode;
import com.zsinda.fdp.entity.SysMenu;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: FDPlatform
 * @description: TreeUtil
 * @author: Sinda
 * @create: 2020-01-02 23:32
 */
@UtilityClass
public class TreeUtil {

    public List<MenuTree> buildTree(List<SysMenu> menus, int root) {
        List<MenuTree> trees = new ArrayList<>();
        MenuTree node;
        for (SysMenu menu : menus) {
            node = new MenuTree();
            node.setId(menu.getMenuId());
            node.setParentId(menu.getParentId());
            node.setName(menu.getName());
            node.setLabel(menu.getName());
            node.setPath(menu.getPath());
            node.setPermission(menu.getPermission());
            node.setIcon(menu.getIcon());
            node.setSort(menu.getSort());
            trees.add(node);
        }
        return TreeUtil.build(trees, root);
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
