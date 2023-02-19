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

package com.xhuicloud.logs.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.AuditRecord;
import com.xhuicloud.logs.entity.AuditRecordModel;
import com.xhuicloud.logs.service.AuditRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
* @program: logs
* @description: 
* @author: Sinda
* @create: 2023-01-31 23:40:23
*/
@RestController
@AllArgsConstructor
@RequestMapping("/auditRecord" )
@Api(value = "auditRecord", tags = "管理")
public class AuditRecordController {

    private final AuditRecordService auditRecordService;

    /**
    * 分页查询
    *
    * @param page 分页对象
    * @param auditRecordModel
    * @return Response
    */
    @GetMapping("/page" )
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public Response page(Page page, AuditRecordModel auditRecordModel) {
        return Response.success(auditRecordService.page(page, Wrappers.query(auditRecordModel).lambda().orderByDesc(AuditRecordModel::getCreateTime)));
    }


    /**
    * 通过id查询
    * @param id
    * @return Response
    */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    public Response getById(@PathVariable Long id) {
        return Response.success(auditRecordService.getById(id));
    }

    /**
    * 新增
    *
    * @param auditRecordModel
    * @return Response
    */
    @AuditRecord("新增" )
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_auditRecord')" )
    @ApiOperation(value = "新增", notes = "新增")
    public Response save(@RequestBody AuditRecordModel auditRecordModel) {
        return Response.success(auditRecordService.save(auditRecordModel));
    }

    /**
    * 修改
    *
    * @param auditRecordModel
    * @return Response
    */
    @AuditRecord("编辑" )
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_auditRecord')" )
    @ApiOperation(value = "修改", notes = "修改")
    public Response update(@RequestBody AuditRecordModel auditRecordModel) {
        return Response.success(auditRecordService.updateById(auditRecordModel));
    }

    /**
    * 通过id删除
    *
    * @param id
    * @return Response
    */
    @AuditRecord("通过id删除" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@authorize.hasPermission('sys_delete_auditRecord')" )
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    public Response delete(@PathVariable Long id) {
        return Response.success(auditRecordService.removeById(id));
    }

}
