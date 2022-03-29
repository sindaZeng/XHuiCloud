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
import com.xhuicloud.common.core.utils.ExcelUtil;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.common.security.annotation.Anonymous;
import com.xhuicloud.common.security.utils.SecurityHolder;
import com.xhuicloud.upms.dto.UserDto;
import com.xhuicloud.upms.entity.SysUser;
import com.xhuicloud.upms.entity.SysUserSocial;
import com.xhuicloud.upms.service.SysUserService;
import com.xhuicloud.upms.service.SysUserSocialService;
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

    private final SysUserSocialService sysUserSocialService;

    /**
     * 分页查询用户列表
     *
     * @param userDto
     * @return
     */
    @GetMapping("/page")
    public Response page(Page page, UserDto userDto) {
        return Response.success(sysUserService.userPage(page, userDto));
    }

    /**
     * 获取当前登录用户全部信息
     *
     * @return
     */
    @GetMapping("/info")
    public Response info() {
        String username = SecurityHolder.getUser().getUsername();
        SysUser user = sysUserService.getOne(Wrappers.<SysUser>query()
                .lambda().eq(SysUser::getUsername, username));
        if (user == null) {
            return Response.failed(null, "获取当前用户信息失败");
        }
        user.setPassword(null);
        return Response.success(sysUserService.getSysUser(user));
    }

    /**
     * 根据用户名 查找用户
     *
     * @param userName
     * @return
     */
    @Anonymous
    @GetMapping("/info/{userName}")
    public Response getSysUser(@PathVariable String userName) {
        SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda()
                .eq(SysUser::getUsername, userName));
        if (user == null) {
            return Response.failed(null, String.format("用户信息为空 %s", userName));
        }
        return Response.success(sysUserService.getSysUser(user));
    }

    /**
     * 查找用户第三方id
     *
     * @return
     */
    @Anonymous
    @GetMapping("/social/{userId}/{type}")
    public Response getUserSocial(@PathVariable("userId") Integer userId, @PathVariable("type") String type) {
        return Response.success(sysUserSocialService.getOne(Wrappers.<SysUserSocial>lambdaQuery()
                .eq(SysUserSocial::getUserId, userId).eq(SysUserSocial::getSocialType, type)));
    }

    /**
     * 添加用户
     *
     * @return
     */
    @SysLog("添加用户")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_user')")
    public Response save(@Valid @RequestBody SysUser sysUser) {
        return Response.success(sysUserService.saveUser(sysUser));
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
    public Response importUser(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> excelUtil = new ExcelUtil<>(SysUser.class);
        List<SysUser> userList = excelUtil.importExcel(file.getInputStream());
        return Response.success(sysUserService.importUser(userList, updateSupport));
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
    public Response update(@Valid @RequestBody SysUser sysUser) {
        return Response.success(sysUserService.updateUser(sysUser));
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
    public Response delete(@PathVariable Integer id) {
        return Response.success(sysUserService.removeById(id));
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
    public Response lock(@PathVariable Integer id) {
        return Response.success(sysUserService.lock(id));
    }
}
