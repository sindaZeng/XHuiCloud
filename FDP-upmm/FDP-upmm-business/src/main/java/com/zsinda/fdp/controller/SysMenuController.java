package com.zsinda.fdp.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zsinda.fdp.annotation.SysLog;
import com.zsinda.fdp.dto.MenuTree;
import com.zsinda.fdp.entity.SysMenu;
import com.zsinda.fdp.service.SysMenuService;
import com.zsinda.fdp.utils.R;
import com.zsinda.fdp.utils.SpringSecurityUtils;
import com.zsinda.fdp.utils.TreeUtil;
import com.zsinda.fdp.vo.MenuVO;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        Set<MenuVO> all = new HashSet<>();
        SpringSecurityUtils.getRoles()
                .forEach(roleId -> all.addAll(sysMenuService.findMenuByRoleId(roleId)));
        List<MenuTree> menuTreeList = all.stream()
                .filter(menuVo -> 0==menuVo.getType())
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
    public R getMenuTree(@RequestParam Boolean disabled) {
        return R.ok(TreeUtil.buildMenuTree(disabled,sysMenuService
                .list(Wrappers.<SysMenu>lambdaQuery()
                        .orderByAsc(SysMenu::getSort)), 0));
    }

    /**
     * 新增菜单
     * @param sysMenu
     * @return
     */
    @SysLog("新增菜单")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_menu')")
    public R save(@Valid @RequestBody SysMenu sysMenu) {
        return R.ok(sysMenuService.saveMenu(sysMenu));
    }

    /**
     * 禁用，启用 菜单
     * @param id
     * @return
     */
    @SysLog("开启禁用菜单")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_menu')")
    public R delete(@PathVariable Integer id) {
        return R.ok(sysMenuService.deleteMenu(id));
    }

    @SysLog("编辑菜单")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_menu')")
    public R update(@Valid @RequestBody SysMenu sysMenu) {
        return R.ok(sysMenuService.updateMenu(sysMenu));
    }
}
