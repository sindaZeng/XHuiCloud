package com.zsinda.fdp.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsinda.fdp.entity.SysRole;
import com.zsinda.fdp.service.SysRoleMenuService;
import com.zsinda.fdp.service.SysRoleService;
import com.zsinda.fdp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 分页查询角色列表
     * @param page
     * @return
     */
    @GetMapping("/pageList")
    public R pageList(Page page) {
        return R.ok(sysRoleService.page(page, Wrappers.<SysRole>lambdaQuery().eq(SysRole::getDelFlag,1)));
    }

    /**
     *  新增角色
     * @param sysRole
     * @return
     */
    @PostMapping("/save")
    public R save(@Valid @RequestBody SysRole sysRole) {
        return R.ok(sysRoleService.getBaseMapper().insert(sysRole));
    }

    /**
     * 更新角色
     * @param sysRole
     * @return
     */
    @PutMapping("/update")
    public R pageList(@Valid @RequestBody SysRole sysRole) {
        return R.ok(sysRoleService.updateById(sysRole));
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable Integer id) {
        return R.ok(sysRoleService.deleteRoleById(id));
    }

    /**
     * 通过ID查询角色信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return R.ok(sysRoleService.getById(id));
    }
    /**
     * 更新角色菜单
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/menus")
    public R saveRoleMenus(Integer roleId, @RequestParam(value = "menuIds", required = false) String menuIds) {
        return R.ok(sysRoleMenuService.saveRoleMenus(roleId, menuIds));
    }



}
