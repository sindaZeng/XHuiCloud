package com.zsinda.fdp.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsinda.fdp.annotation.Inner;
import com.zsinda.fdp.annotation.SysLog;
import com.zsinda.fdp.dto.UserDto;
import com.zsinda.fdp.entity.SysUser;
import com.zsinda.fdp.service.SysUserService;
import com.zsinda.fdp.utils.R;
import com.zsinda.fdp.utils.SpringSecurityUtils;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Api(value = "user",tags = "用户管理模块")
public class SysUserController {

    private final SysUserService sysUserService;

    /**
     * 分页查询用户列表
     * @param userDto
     * @return
     */
    @GetMapping("/userPage")
    public R userPage(Page page, UserDto userDto) {
        return R.ok(sysUserService.userPage(page,userDto));
    }

    /**
     * 获取当前登录用户全部信息
     */
    @GetMapping("/info")
    public R info() {
        String username = SpringSecurityUtils.getUser().getUsername();
        SysUser user = sysUserService.getOne(Wrappers.<SysUser>query()
                .lambda().eq(SysUser::getUsername, username));
        if (user == null) {
            return R.failed(null, "获取当前用户信息失败");
        }
        return R.ok(sysUserService.findUserInfo(user));
    }

    @Inner
    @GetMapping("/getSysUser/{userName}")
    public R getSysUser(@PathVariable String userName){

        SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, userName));
        if (user == null) {
            return R.failed(null, String.format("用户信息为空 %s", userName));
        }
        return R.ok(sysUserService.findUserInfo(user));
    }

    /**
     * 添加用户
     * @return
     */
    @Inner
    @SysLog("添加用户")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_user')")
    public R user(@Valid @RequestBody SysUser sysUser) {
        return R.ok(sysUserService.save(sysUser));
    }

    /**
     * 编辑用户
     * @param sysUser
     * @return
     */
    @SysLog("编辑用户")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_user')")
    public R update(@Valid @RequestBody SysUser sysUser) {
        return R.ok(sysUserService.updateUser(sysUser));
    }

    /**
     * 开启禁用用户
     * @param id
     * @return
     */
    @SysLog("开启/禁用用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_user')")
    public R delete(@PathVariable Integer id) {
        for (int i = 0; i < 10; i++) {
            continue;
        }
        return R.ok(sysUserService.deleteUser(id));
    }

    /**
     * 锁定/解锁用户
     * @param id
     * @return
     */
    @SysLog("锁定/解锁用户")
    @GetMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_ban_user')")
    public R lock(@PathVariable Integer id) {
        return R.ok(sysUserService.lock(id));
    }
}
