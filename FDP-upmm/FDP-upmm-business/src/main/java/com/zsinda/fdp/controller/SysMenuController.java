package com.zsinda.fdp.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zsinda.fdp.dto.MenuTree;
import com.zsinda.fdp.entity.SysMenu;
import com.zsinda.fdp.service.SysMenuService;
import com.zsinda.fdp.utils.R;
import com.zsinda.fdp.utils.SpringSecurityUtils;
import com.zsinda.fdp.utils.TreeUtil;
import com.zsinda.fdp.vo.MenuVO;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: FDPlatform
 * @description: SysMenuController
 * @author: Sinda
 * @create: 2020-01-02 23:26
 */
@RestController
@RequestMapping("/menu")
@AllArgsConstructor
@Api(value = "menu",tags = "菜单管理模块")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    /**
     * 返回当前用户的树形菜单集合
     * @return 当前用户的树形菜单
     */
    @GetMapping
    public R getUserMenu() {
        String MENU="0";
        Set<MenuVO> all = new HashSet<>();
        SpringSecurityUtils.getRoles()
                .forEach(roleId -> all.addAll(sysMenuService.findMenuByRoleId(roleId)));
        List<MenuTree> menuTreeList = all.stream()
                .filter(menuVo -> MENU.equals(menuVo.getType()))
                .map(MenuTree::new)
                .sorted(Comparator.comparingInt(MenuTree::getSort))
                .collect(Collectors.toList());


        return R.ok(TreeUtil.build(menuTreeList,0));
    }

    /**
     * 返回角色的菜单集合
     * @param roleId
     * @return
     */
    @GetMapping("/tree/{roleId}")
    public R getRoleTree(@PathVariable Integer roleId) {
        return R.ok(sysMenuService.findMenuByRoleId(roleId)
                .stream()
                .map(MenuVO::getMenuId)
                .collect(Collectors.toList()));
    }

    /**
     * 树形菜单
     * @return
     */
    @GetMapping(value = "/tree")
    public R getMenuTree() {
        return R.ok(TreeUtil.buildTree(sysMenuService
                .list(Wrappers.<SysMenu>lambdaQuery()
                        .orderByAsc(SysMenu::getSort)), 0));
    }

}
