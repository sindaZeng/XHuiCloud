package com.xhuicloud.upms.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.utils.ExcelUtil;
import com.xhuicloud.common.core.utils.R;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.common.security.annotation.Inner;
import com.xhuicloud.common.security.utils.SecurityHolder;
import com.xhuicloud.upms.dto.UserDto;
import com.xhuicloud.upms.entity.SysUser;
import com.xhuicloud.upms.service.SysUserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Api(value = "user", tags = "用户管理模块")
public class SysUserController {

    private final SysUserService sysUserService;

    /**
     * 分页查询用户列表
     *
     * @param userDto
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, UserDto userDto) {
        return R.ok(sysUserService.userPage(page, userDto));
    }

    /**
     * 获取当前登录用户全部信息
     *
     * @return
     */
    @GetMapping("/info")
    public R info() {
        String username = SecurityHolder.getUser().getUsername();
        SysUser user = sysUserService.getOne(Wrappers.<SysUser>query()
                .lambda().eq(SysUser::getUsername, username));
        if (user == null) {
            return R.failed(null, "获取当前用户信息失败");
        }
        return R.ok(sysUserService.getSysUser(user));
    }

    /**
     * 根据用户名 查找用户
     *
     * @param userName
     * @return
     */
    @Inner
    @GetMapping("/{userName}")
    public R getSysUser(@PathVariable String userName) {
        SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda()
                .eq(SysUser::getUsername, userName)
                .eq(SysUser::getIsDel, 1));
        if (user == null) {
            return R.failed(null, String.format("用户信息为空 %s", userName));
        }
        return R.ok(sysUserService.getSysUser(user));
    }

    /**
     * 添加用户
     *
     * @return
     */
    @SysLog("添加用户")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_user')")
    public R save(@Valid @RequestBody SysUser sysUser) {
        return R.ok(sysUserService.saveUser(sysUser));
    }

    /**
     * 导入用户
     *
     * @param file          文件
     * @param updateSupport 是否更新已存在的用户
     * @return
     * @throws Exception
     */
    @SysLog("导入用户")
    @PostMapping("/import")
    @PreAuthorize("@authorize.hasPermission('sys_import_user')")
    public R importUser(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> excelUtil = new ExcelUtil<>(SysUser.class);
        List<SysUser> userList = excelUtil.importExcel(file.getInputStream());
        return R.ok(sysUserService.importUser(userList, updateSupport));
    }

    /**
     * 编辑用户
     *
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
     *
     * @param id
     * @return
     */
    @SysLog("开启/禁用用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_user')")
    public R delete(@PathVariable Integer id) {
        return R.ok(sysUserService.deleteUser(id));
    }

    /**
     * 锁定/解锁用户
     *
     * @param id
     * @return
     */
    @SysLog("锁定/解锁用户")
    @PostMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_ban_user')")
    public R lock(@PathVariable Integer id) {
        return R.ok(sysUserService.lock(id));
    }
}
