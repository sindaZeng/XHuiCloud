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

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.authorization.resource.annotation.Anonymous;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.gateway.support.ClientDetailsInitEvent;
import com.xhuicloud.common.log.annotation.AuditRecord;
import com.xhuicloud.common.mybatis.utils.PageConvertor;
import com.xhuicloud.upms.entity.SysClientDetails;
import com.xhuicloud.upms.service.SysClientDetailsService;
import com.xhuicloud.upms.vo.ClientVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
@Api(value = "client", tags = "客户端授权管理")
public class SysClientDetailController {

    private final SysClientDetailsService sysClientDetailsService;

    @Anonymous(false)
    @GetMapping("/{clientId}")
    public Response<SysClientDetails> getById(@PathVariable(value = "clientId") String clientId) {
        return Response.success(sysClientDetailsService.getByIdOrClientId(clientId));
    }

    @GetMapping("/getClientSecretById/{clientId}")
    public Response<String> getClientSecretById(@PathVariable(value = "clientId") String clientId) {
        return Response.success(sysClientDetailsService.getByIdOrClientId(clientId).getClientSecret());
    }

    /**
     * 分页查询
     *
     * @param page             分页对象
     * @param sysClientDetails 终端信息
     * @return Response
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public Response<Page<ClientVo>> page(Page page, SysClientDetails sysClientDetails) {
        Page<ClientVo> clientVoPage = PageConvertor.convertPage(sysClientDetailsService.page(page, Wrappers.query(sysClientDetails)), ClientVo.class);
        return Response.success(clientVoPage);
    }

    /**
     * 新增终端信息
     *
     * @param sysClientDetails 终端信息
     * @return Response
     */
    @AuditRecord("新增终端信息")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_client')")
    @ApiOperation(value = "新增终端信息", notes = "新增终端信息")
    public Response<Boolean> save(@RequestBody SysClientDetails sysClientDetails) {
        sysClientDetailsService.save(sysClientDetails);
        SpringUtil.publishEvent(new ClientDetailsInitEvent(sysClientDetails));
        return Response.success(Boolean.TRUE);
    }

    /**
     * 修改终端信息
     *
     * @param sysClientDetails 终端信息
     * @return Response
     */
    @AuditRecord("编辑终端信息")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_client')")
    @ApiOperation(value = "修改终端信息", notes = "修改终端信息")
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS, key = "#sysClientDetails.clientId")
    public Response<Boolean> update(@RequestBody SysClientDetails sysClientDetails) {
        sysClientDetails.setClientSecret(null);
        sysClientDetailsService.updateById(sysClientDetails);
        SpringUtil.publishEvent(new ClientDetailsInitEvent(sysClientDetails));
        return Response.success(Boolean.TRUE);
    }

    /**
     * 通过id删除终端信息
     *
     * @param id
     * @return Response
     */
    @AuditRecord("通过id删除终端信息")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_client')")
    @ApiOperation(value = "通过id删除终端信息", notes = "通过id删除终端信息")
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS, key = "#sysClientDetails.clientId")
    public Response<Boolean> delete(@PathVariable Long id) {
        sysClientDetailsService.removeById(id);
        SpringUtil.publishEvent(new ClientDetailsInitEvent(id));
        return Response.success(Boolean.TRUE);
    }

}
