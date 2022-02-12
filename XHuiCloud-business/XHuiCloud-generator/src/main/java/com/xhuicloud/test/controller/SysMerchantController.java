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

package com.xhuicloud.test.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.test.entity.SysMerchant;
import com.xhuicloud.test.service.SysMerchantService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
* @program: Test
* @description: 商户表
* @author: Sinda
* @create: 2022-02-11 17:44:19
*/
@RestController
@AllArgsConstructor
@RequestMapping("/sysMerchant" )
@Api(value = "sysMerchant", tags = "商户表管理")
public class SysMerchantController {

    private final SysMerchantService sysMerchantService;

    /**
    * 分页查询
    *
    * @param page 分页对象
    * @param sysMerchant 商户表
    * @return Response
    */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public Response page(Page page, SysMerchant sysMerchant) {
        return Response.success(sysMerchantService.page(page, Wrappers.query(sysMerchant)));
    }


    /**
    * 通过id查询商户表
    * @param id
    * @return Response
    */
    @ApiOperation(value = "通过id查询商户表", notes = "通过id查询商户表")
    @GetMapping("/{id}")
    public Response getById(@PathVariable Integer id) {
        return Response.success(sysMerchantService.getById(id));
    }

    /**
    * 新增商户表
    *
    * @param sysMerchant 商户表
    * @return Response
    */
    @ApiOperation(value = "新增商户表", notes = "新增商户表")
    @SysLog("新增商户表" )
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_sysMerchant')" )
    public Response save(@RequestBody SysMerchant sysMerchant) {
        return Response.success(sysMerchantService.save(sysMerchant));
    }

    /**
    * 修改商户表
    *
    * @param sysMerchant 商户表
    * @return Response
    */
    @ApiOperation(value = "修改商户表", notes = "修改商户表")
    @SysLog("编辑商户表" )
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_sysMerchant')" )
    public Response update(@RequestBody SysMerchant sysMerchant) {
        return Response.success(sysMerchantService.updateById(sysMerchant));
    }

    /**
    * 通过id删除商户表
    *
    * @param id
    * @return Response
    */
    @ApiOperation(value = "通过id删除商户表", notes = "通过id删除商户表")
    @SysLog("通过id删除商户表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@authorize.hasPermission('sys_delete_sysMerchant')" )
    public Response delete(@PathVariable Integer id) {
        return Response.success(sysMerchantService.removeById(id));
    }

}
