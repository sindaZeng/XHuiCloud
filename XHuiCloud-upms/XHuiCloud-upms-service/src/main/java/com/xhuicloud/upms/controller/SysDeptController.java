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
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.AuditRecord;
import com.xhuicloud.upms.dto.DeptTree;
import com.xhuicloud.upms.entity.SysDept;
import com.xhuicloud.upms.service.SysDeptService;
import com.xhuicloud.upms.utils.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @program: admin
 * @description: 部门
 * @author: Sinda
 * @create: 2022-04-21 22:24:04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dept" )
@Api(value = "dept", tags = "部门管理")
public class SysDeptController {

    private final SysDeptService sysDeptService;

    /**
     * 树形部门
     *
     * @return
     */
    @GetMapping(value = "/tree")
    public Response<List<DeptTree>> getDeptTree() {
        return Response.success(TreeUtil.buildDeptTree(sysDeptService
                .list(Wrappers.<SysDept>lambdaQuery()
                        .orderByAsc(SysDept::getSort)), 0l));
    }

    /**
     * 分页查询
     *
     * @param page 分页对象
     * @param sysDept 部门
     * @return Response
     */
    @GetMapping("/page" )
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public Response<Page> page(Page page, SysDept sysDept) {
        return Response.success(sysDeptService.page(page, Wrappers.query(sysDept)));
    }


    /**
     * 通过id查询部门
     * @param id
     * @return Response
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询部门", notes = "通过id查询部门")
    public Response<SysDept> getById(@PathVariable Long id) {
        return Response.success(sysDeptService.getById(id));
    }

    /**
     * 新增部门
     *
     * @param sysDept 部门
     * @return Response
     */
    @AuditRecord("新增部门" )
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_dept')" )
    @ApiOperation(value = "新增部门", notes = "新增部门")
    public Response<Boolean> save(@RequestBody SysDept sysDept) {
        return Response.success(sysDeptService.save(sysDept));
    }

    /**
     * 修改部门
     *
     * @param sysDept 部门
     * @return Response
     */
    @AuditRecord("编辑部门" )
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_dept')" )
    @ApiOperation(value = "修改部门", notes = "修改部门")
    public Response<Boolean> update(@RequestBody SysDept sysDept) {
        return Response.success(sysDeptService.updateById(sysDept));
    }

    /**
     * 通过id删除部门
     *
     * @param id
     * @return Response
     */
    @AuditRecord("通过id删除部门" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@authorize.hasPermission('sys_delete_dept')" )
    @ApiOperation(value = "通过id删除部门", notes = "通过id删除部门")
    public Response<Boolean> delete(@PathVariable Long id) {
        return Response.success(sysDeptService.removeById(id));
    }

}
