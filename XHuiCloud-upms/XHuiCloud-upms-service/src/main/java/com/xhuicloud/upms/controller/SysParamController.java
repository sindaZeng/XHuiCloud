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
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.common.authorization.resource.annotation.Anonymous;
import com.xhuicloud.common.authorization.resource.utils.SecurityHolder;
import com.xhuicloud.upms.entity.SysParam;
import com.xhuicloud.upms.service.SysParamService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: XHuiCloud
 * @description: SysConfigController
 * @author: Sinda
 * @create: 2020-05-09 12:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/param")
@Api(value = "param", tags = "系统公共参数模块")
public class SysParamController {

    private final SysParamService sysParamService;

    /**
     * 分页查询系统参数列表
     *
     * @return
     */
    @GetMapping("/page")
    public Response page(Page page) {
        return Response.success(sysParamService.page(page));
    }

    /**
     * 新增系统参数
     *
     * @param sysParam
     * @return
     */
    @SysLog("新增系统参数")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_param')")
    public Response save(@Valid @RequestBody SysParam sysParam) {
        return Response.success(sysParamService.saveParam(sysParam));
    }

    /**
     * 获取一个系统参数
     *
     * @return
     */
    @Anonymous(value = false)
    @GetMapping("/get")
    public Response<SysParam> get(@RequestParam String key) {
        return Response.success(sysParamService.getSysParamByKey(key));
    }

    /**
     * 更新系统参数
     *
     * @param sysParam
     * @return
     */
    @SysLog("更新系统参数")
    @PutMapping("/updateValue")
    @Anonymous(value = false)
    @CacheEvict(value = CacheConstants.SYS_PARAM,  key = "#sysParam.paramKey", allEntries = true)
    public Response updateValue(@RequestBody SysParam sysParam) {
        SysParam sysParamByKey = sysParamService.getSysParamByKey(sysParam.getParamKey());
        sysParamByKey.setParamValue(sysParam.getParamValue());
        return Response.success(sysParamService.updateById(sysParamByKey));
    }

    /**
     * 更新系统参数
     *
     * @param sysParam
     * @return
     */
    @SysLog("更新系统参数")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_param')")
    @CacheEvict(value = CacheConstants.SYS_PARAM,  key = "#sysParam.paramKey", allEntries = true)
    public Response update(@Valid @RequestBody SysParam sysParam) {
        sysParam.setUpdateId(SecurityHolder.getUserId());
        return Response.success(sysParamService.updateById(sysParam));
    }

    /**
     * 删除系统参数
     *
     * @param id
     * @return
     */
    @SysLog("删除系统参数")
    @PreAuthorize("@authorize.hasPermission('sys_delete_param')")
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Integer id) {
        return Response.success(sysParamService.removeById(id));
    }
}


