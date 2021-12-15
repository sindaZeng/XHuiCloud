package com.xhuicloud.upms.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.common.security.utils.SecurityHolder;
import com.xhuicloud.upms.dto.MenuTree;
import com.xhuicloud.upms.entity.SysMenu;
import com.xhuicloud.upms.service.SysMenuService;
import com.xhuicloud.upms.utils.TreeUtil;
import com.xhuicloud.upms.vo.MenuVO;
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
 * @program: XHuiCloud
 * @description: SysMenuController
 * @author: Sinda
 * @create: 2020-01-02 23:26
 */
@RestController
@RequestMapping("/menu")
@AllArgsConstructor
@Api(value = "menu", tags = "菜单管理模块")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    /**
     * 返回当前用户的树形菜单集合
     *
     * @return 当前用户的树形菜单
     */
    @GetMapping
    public Response getUserMenu() {
        Set<MenuVO> all = new HashSet<>();
        SecurityHolder.getRoles()
                .forEach(roleCode -> all.addAll(sysMenuService.findMenuByRoleCode(roleCode)));
        List<MenuTree> menuTreeList = all.stream()
                .filter(menuVo -> 0 == menuVo.getType())
                .map(MenuTree::new)
                .sorted(Comparator.comparingInt(MenuTree::getSort))
                .collect(Collectors.toList());
        return Response.success(TreeUtil.build(menuTreeList, 0));
    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleId
     * @return
     */
    @GetMapping("/tree/{roleId}")
    public Response getRoleTree(@PathVariable Integer roleId) {
        return Response.success(sysMenuService.findMenuByRoleId(roleId)
                .stream()
                .map(MenuVO::getId)
                .collect(Collectors.toList()));
    }

    /**
     * 树形菜单
     *
     * @return
     */
    @GetMapping(value = "/tree")
    public Response getMenuTree(@RequestParam Boolean disabled) {
        return Response.success(TreeUtil.buildMenuTree(disabled, sysMenuService
                .list(Wrappers.<SysMenu>lambdaQuery()
                        .orderByAsc(SysMenu::getSort)), 0));
    }

    /**
     * 新增菜单
     *
     * @param sysMenu
     * @return
     */
    @SysLog("新增菜单")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_menu')")
    public Response save(@Valid @RequestBody SysMenu sysMenu) {
        return Response.success(sysMenuService.saveMenu(sysMenu));
    }

    /**
     * 禁用，启用 菜单
     *
     * @param id
     * @return
     */
    @SysLog("开启禁用菜单")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_menu')")
    public Response delete(@PathVariable Integer id) {
        return Response.success(sysMenuService.deleteMenu(id));
    }

    /**
     * 编辑菜单
     *
     * @param sysMenu
     * @return
     */
    @SysLog("编辑菜单")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_menu')")
    public Response update(@Valid @RequestBody SysMenu sysMenu) {
        return Response.success(sysMenuService.updateMenu(sysMenu));
    }
}
