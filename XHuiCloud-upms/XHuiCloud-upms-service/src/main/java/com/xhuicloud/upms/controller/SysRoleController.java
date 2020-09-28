package com.xhuicloud.upms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.utils.R;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.upms.dto.RoleDto;
import com.xhuicloud.upms.entity.SysRole;
import com.xhuicloud.upms.service.SysRoleMenuService;
import com.xhuicloud.upms.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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
    @ApiOperation(value = "分页查询角色列表", notes = "分页查询角色列表")
    public R page(Page page) {
        return R.ok(sysRoleService.page(page));
    }

    /**
     * 查询角色列表
     *
     * @return
     */
    @ApiOperation(value = "查询角色列表", notes = "查询角色列表")
    @GetMapping("/list")
    public R list() {
        return R.ok(sysRoleService.list()
                .stream().map(RoleDto::new).collect(Collectors.toList()));
    }

    /**
     * 新增角色
     *
     * @param sysRole
     * @return
     */
    @SysLog("新增角色")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_role')")
    @CacheEvict(value = CacheConstants.SYS_ROLE_IDS, allEntries = true)
    @ApiOperation(value = "新增角色", notes = "新增角色")
    public R save(@Valid @RequestBody SysRole sysRole) {
        return R.ok(sysRoleService.save(sysRole));
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
    @CacheEvict(value = CacheConstants.SYS_ROLE_IDS, allEntries = true)
    @ApiOperation(value = "编辑角色", notes = "编辑角色")
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
    @CacheEvict(value = CacheConstants.SYS_ROLE_IDS, allEntries = true)
    @ApiOperation(value = "删除角色", notes = "删除角色")
    public R delete(@PathVariable Integer id) {
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
