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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.xhuicloud.common.authorization.resource.annotation.Anonymous;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.AuditRecord;
import com.xhuicloud.upms.dto.TenantDto;
import com.xhuicloud.upms.entity.SysSocial;
import com.xhuicloud.upms.entity.SysTenant;
import com.xhuicloud.upms.service.SysSocialService;
import com.xhuicloud.upms.service.SysTenantService;
import com.xhuicloud.upms.vo.SocialVo;
import com.xhuicloud.upms.vo.TenantVo;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private final SysSocialService sysSocialService;

    /**
     * 查询系统租户列表
     *
     * @return
     */
    @Anonymous(value = false)
    @GetMapping("/list")
    public Response<List<TenantVo>> list(@RequestParam(required = false) String name) {
        List<SysTenant> sysTenants = sysTenantService.list(Wrappers.query(SysTenant.builder().state(1).name(name).build()));
        Map<Long, List<SysSocial>> sysSocialMap = sysSocialService.list()
                .stream().collect(Collectors
                        .toMap(SysSocial::getTenantId,
                                sysSocial -> Lists.newArrayList(sysSocial),
                                (oldVal, newVal) -> {
                                    oldVal.addAll(newVal);
                                    return oldVal;
                                }));
        List<TenantVo> tenantVos = sysTenants.stream().map(sysTenant -> {
            TenantVo tenantVo = new TenantVo();
            BeanUtil.copyProperties(sysTenant, tenantVo);
            if (!sysSocialMap.isEmpty()) {
                List<SysSocial> sysSocials = sysSocialMap.get(sysTenant.getId());
                if (CollectionUtil.isNotEmpty(sysSocials))
                    tenantVo.setSocials(sysSocials.stream().collect(Collectors.toMap(SysSocial::getType, x -> {
                        SocialVo socialVo = new SocialVo();
                        socialVo.setAppId(x.getAppId());
                        socialVo.setRedirectUrl(x.getRedirectUrl());
                        return socialVo;
                    })));
            }
            return tenantVo;
        }).collect(Collectors.toList());
        return Response.success(tenantVos);
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
    public Response<Page> page(Page page, SysTenant sysTenant) {
        return Response.success(sysTenantService.page(page, Wrappers.query(sysTenant)));
    }

    /**
     * 添加租户
     *
     * @return
     */
    @AuditRecord("添加租户")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_tenant')")
    public Response<Boolean> save(@Valid @RequestBody SysTenant sysTenant) {
        return Response.success(sysTenantService.save(sysTenant));
    }

    /**
     * 编辑租户
     *
     * @return
     */
    @AuditRecord("编辑租户")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_tenant')")
    public Response<Boolean> update(@Valid @RequestBody SysTenant sysTenant) {
        return Response.success(sysTenantService.updateById(sysTenant));
    }

    /**
     * 删除租户
     *
     * @param id
     * @return
     */
    @AuditRecord("删除租户")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_tenant')")
    public Response<Boolean> delete(@PathVariable Long id) {
        return Response.success(sysTenantService.removeById(id));
    }

    /**
     * 改变租户状态
     *
     * @param id
     * @param state
     * @return
     */
    @AuditRecord("改变租户状态")
    @GetMapping("/state")
    @PreAuthorize("@authorize.hasPermission('sys_delete_tenant')")
    public Response<Boolean> state(@RequestParam(value = "id") Long id, @RequestParam(value = "state") Integer state) {
        return Response.success(sysTenantService.state(id, state));
    }

}


