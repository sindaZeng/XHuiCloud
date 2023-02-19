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

package com.xhuicloud.upms.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.AuditRecord;
import com.xhuicloud.upms.dto.RoleDto;
import com.xhuicloud.upms.dto.RoleMenusDto;
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
import java.util.List;
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
    public Response<Page> page(Page page, SysRole sysRole) {
        return Response.success(sysRoleService.page(page, Wrappers.query(sysRole)));
    }

    /**
     * 查询角色列表
     *
     * @return
     */
    @ApiOperation(value = "查询角色列表", notes = "查询角色列表")
    @GetMapping("/list")
    public Response<List<RoleDto>> list() {
        return Response.success(sysRoleService.list()
                .stream().map(RoleDto::new).collect(Collectors.toList()));
    }

    /**
     * 新增角色
     *
     * @param sysRole
     * @return
     */
    @AuditRecord("新增角色")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_role')")
    @CacheEvict(value = CacheConstants.SYS_ROLE, allEntries = true)
    @ApiOperation(value = "新增角色", notes = "新增角色")
    public Response<Boolean> save(@Valid @RequestBody SysRole sysRole) {
        return Response.success(sysRoleService.save(sysRole));
    }

    /**
     * 更新角色
     *
     * @param sysRole
     * @return
     */
    @AuditRecord("编辑角色")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_role')")
    @CacheEvict(value = CacheConstants.SYS_ROLE, allEntries = true)
    @ApiOperation(value = "编辑角色", notes = "编辑角色")
    public Response<Boolean> update(@Valid @RequestBody SysRole sysRole) {
        return Response.success(sysRoleService.updateById(sysRole));
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @AuditRecord("删除角色")
    @PreAuthorize("@authorize.hasPermission('sys_delete_role')")
    @DeleteMapping("/{id}")
    @CacheEvict(value = CacheConstants.SYS_ROLE, allEntries = true)
    @ApiOperation(value = "删除角色", notes = "删除角色")
    public Response<Boolean> delete(@PathVariable Long id) {
        return Response.success(sysRoleService.deleteRoleById(id));
    }

    /**
     * 通过ID查询角色信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Response<SysRole> getById(@PathVariable Long id) {
        return Response.success(sysRoleService.getById(id));
    }

    /**
     * 更新角色菜单
     *
     * @param roleMenusDto
     * @return
     */
    @AuditRecord("更新角色菜单")
    @PreAuthorize("@authorize.hasPermission('sys_permission_role')")
    @PutMapping("/menus")
    public Response<Boolean> saveRoleMenus(@RequestBody RoleMenusDto roleMenusDto) {
        return Response.success(sysRoleMenuService.saveRoleMenus(roleMenusDto));
    }

}
