package com.xhuicloud.upms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.common.security.annotation.NoAuth;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysSocial;
import com.xhuicloud.upms.service.SysSocialService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: XHuiCloud
 * @description: SysSocialController
 * @author: Sinda
 * @create: 2020-06-17 12:31
 */
@RestController
@RequestMapping("/social")
@AllArgsConstructor
@Api(value = "social", tags = "第三社交模块")
public class SysSocialController {

    private final SysSocialService sysSocialService;

    /**
     * 渠道授权码查询用户
     * 第三方社交登录
     *
     * @param auth_code
     * @return
     */
    @NoAuth
    @GetMapping("/{auth_code}")
    public Response<UserInfo> getSysUser(@PathVariable String auth_code) {
        return Response.success(sysSocialService.getSysUser(auth_code));
    }

    /**
     * 分页查询 社交列表
     *
     * @return
     */
    @GetMapping("/page")
    public Response page(Page page) {
        return Response.success(sysSocialService.page(page));
    }

    /**
     * 新增 社交
     *
     * @param sysSocial
     * @return
     */
    @SysLog("新增社交")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_social')")
    public Response save(@Valid @RequestBody SysSocial sysSocial) {
        return Response.success(sysSocialService.save(sysSocial));
    }

    /**
     * 编辑社交
     *
     * @param sysSocial
     * @return
     */
    @SysLog("编辑社交")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_social')")
    public Response update(@Valid @RequestBody SysSocial sysSocial) {
        return Response.success(sysSocialService.updateById(sysSocial));
    }

    /**
     * 删除社交
     *
     * @param id
     * @return
     */
    @SysLog("开启禁用社交")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_social')")
    public Response delete(@PathVariable Integer id) {
        return Response.success(sysSocialService.removeById(id));
    }

}
