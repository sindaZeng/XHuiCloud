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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.authorization.resource.annotation.Anonymous;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.AuditRecord;
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
     * @param code
     * @return
     */
    @Anonymous
    @GetMapping("/{type}/{code}")
    public Response<UserInfo> getSysUser(@PathVariable(value = "type") String type, @PathVariable String code) {
        return Response.success(sysSocialService.getSysUser(type, code));
    }

    /**
     * 分页查询 社交列表
     *
     * @return
     */
    @GetMapping("/page")
    public Response<Page> page(Page page) {
        return Response.success(sysSocialService.page(page));
    }

    /**
     * 新增 社交
     *
     * @param sysSocial
     * @return
     */
    @AuditRecord("新增社交")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_social')")
    public Response<Boolean> save(@Valid @RequestBody SysSocial sysSocial) {
        return Response.success(sysSocialService.save(sysSocial));
    }

    /**
     * 编辑社交
     *
     * @param sysSocial
     * @return
     */
    @AuditRecord("编辑社交")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_social')")
    public Response<Boolean> update(@Valid @RequestBody SysSocial sysSocial) {
        return Response.success(sysSocialService.updateById(sysSocial));
    }

    /**
     * 删除社交
     *
     * @param id
     * @return
     */
    @AuditRecord("开启禁用社交")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_social')")
    public Response<Boolean> delete(@PathVariable Long id) {
        return Response.success(sysSocialService.removeById(id));
    }

}
