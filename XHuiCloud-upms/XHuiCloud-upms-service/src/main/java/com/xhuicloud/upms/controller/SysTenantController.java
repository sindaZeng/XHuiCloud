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

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.common.security.annotation.Anonymous;
import com.xhuicloud.upms.dto.TenantDto;
import com.xhuicloud.upms.entity.SysTenant;
import com.xhuicloud.upms.service.SysTenantService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: XHuiCloud
 * @description: SysTenantController
 * @author: Sinda
 * @create: 2020-05-13 15:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
@Api(value = "tenant", tags = "系统租户模块")
public class SysTenantController {

    private final SysTenantService sysTenantService;

    /**
     * 查询系统租户列表
     *
     * @return
     */
    @Anonymous(value = false)
    @GetMapping("/list")
    public Response<List<SysTenant>> list() {
        return Response.success(sysTenantService.list(Wrappers.<SysTenant>lambdaQuery()
                .eq(SysTenant::getState, 1)));
    }

    /**
     * 模糊查询租户列表
     *
     * @return
     */
    @Anonymous(value = false)
    @GetMapping("/like")
    public Response<List<TenantDto>> like(@RequestParam(required = false) String tenantName) {
        LambdaQueryWrapper<SysTenant> wrapper = Wrappers.<SysTenant>lambdaQuery()
                .eq(SysTenant::getState, 1);
        if (StrUtil.isNotEmpty(tenantName)) {
            wrapper.like(SysTenant::getName, tenantName);
        }
        return Response.success(TenantDto.build(null, sysTenantService.list(wrapper)));
    }

    /**
     * 分页查询租户列表
     *
     * @param page
     * @return
     */
    @GetMapping("/page")
    public Response page(Page page, SysTenant sysTenant) {
        return Response.success(sysTenantService.page(page,  Wrappers.query(sysTenant)));
    }

    /**
     * 添加租户
     *
     * @return
     */
    @SysLog("添加租户")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_tenant')")
    public Response save(@Valid @RequestBody SysTenant sysTenant) {
        return Response.success(sysTenantService.save(sysTenant));
    }

    /**
     * 编辑租户
     *
     * @return
     */
    @SysLog("编辑租户")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_tenant')")
    public Response update(@Valid @RequestBody SysTenant sysTenant) {
        return Response.success(sysTenantService.updateById(sysTenant));
    }

    /**
     * 开启禁用租户
     *
     * @param id
     * @return
     */
    @SysLog("开启/禁用租户")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_tenant')")
    public Response delete(@PathVariable Integer id) {
        return Response.success(sysTenantService.deleteTenant(id));
    }

    /**
     * 改变租户状态
     *
     * @param id
     * @return
     */
    @SysLog("改变租户状态")
    @GetMapping("/state")
    @PreAuthorize("@authorize.hasPermission('sys_delete_tenant')")
    public Response state(@RequestParam(value = "id") Integer id, @RequestParam(value = "state") Integer state) {
        return Response.success(sysTenantService.state(id, state));
    }

}


