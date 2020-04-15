package com.zsinda.fdp.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsinda.fdp.annotation.SysLog;
import com.zsinda.fdp.dto.RoleDto;
import com.zsinda.fdp.entity.SysRole;
import com.zsinda.fdp.service.SysRoleMenuService;
import com.zsinda.fdp.service.SysRoleService;
import com.zsinda.fdp.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
@Api(value = "role", tags = "角色管理模块")
public class SysRoleController {

    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;

    /**
     * 分页查询角色列表
     *
     * @param page
     * @return
     */
    @GetMapping("/page")
    public R pageList(Page page) {
        return R.ok(sysRoleService.page(page, Wrappers.<SysRole>lambdaQuery().eq(SysRole::getDelFlag, 1)));
    }

    /**
     * 查询角色列表
     *
     * @return
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(sysRoleService.list(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getDelFlag, 1))
                .stream().map(RoleDto::new).collect(Collectors.toList()));
    }

    /**
     * 新增角色
     *
     * @param sysRole
     * @return
     */
    @SysLog("新增角色")
    @PostMapping("/save")
    @PreAuthorize("@authorize.hasPermission('sys_add_role')")
    public R save(@Valid @RequestBody SysRole sysRole) {
        return R.ok(sysRoleService.getBaseMapper().insert(sysRole));
    }

    /**
     * 更新角色
     *
     * @param sysRole
     * @return
     */
    @SysLog("编辑角色")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_role')")
    public R update(@Valid @RequestBody SysRole sysRole) {
        return R.ok(sysRoleService.updateById(sysRole));
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @SysLog("删除角色")
    @PreAuthorize("@authorize.hasPermission('sys_delete_role')")
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable Integer id) {
        return R.ok(sysRoleService.deleteRoleById(id));
    }

    /**
     * 通过ID查询角色信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return R.ok(sysRoleService.getById(id));
    }

    /**
     * 更新角色菜单
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    @SysLog("更新角色菜单")
    @PreAuthorize("@authorize.hasPermission('sys_permission_role')")
    @PostMapping("/menus")
    public R saveRoleMenus(Integer roleId, @RequestParam(value = "menuIds", required = false) String menuIds) {
        return R.ok(sysRoleMenuService.saveRoleMenus(roleId, menuIds));
    }


}
