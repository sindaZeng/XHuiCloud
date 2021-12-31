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
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.upms.entity.SysDept;
import com.xhuicloud.upms.service.SysDeptService;
import com.xhuicloud.upms.utils.TreeUtil;
import com.xhuicloud.upms.vo.DeptVo;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: XHuiCloud
 * @description: SysDeptController
 * @author: Sinda
 * @create: 2020-03-21 15:48
 */
@RestController
@RequestMapping("/dept")
@AllArgsConstructor
@Api(value = "dept", tags = "部门管理模块")
public class SysDeptController {

    private final SysDeptService sysDeptService;

    /**
     * 树形部门
     *
     * @return
     */
    @GetMapping(value = "/tree")
    public Response getDeptTree() {
        return Response.success(TreeUtil.buildDeptTree(sysDeptService
                .list(Wrappers.<SysDept>lambdaQuery()
                        .orderByAsc(SysDept::getSort)), 0));
    }

    /**
     * 新增部门
     *
     * @param sysDept
     * @return
     */
    @SysLog("新增部门")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_dept')")
    public Response save(@Valid @RequestBody SysDept sysDept) {
        return Response.success(sysDeptService.saveDept(sysDept));
    }

    /**
     * 禁用，启用 部门
     *
     * @param id
     * @return
     */
    @SysLog("开启禁用部门")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_dept')")
    public Response delete(@PathVariable Integer id) {
        return Response.success(sysDeptService.deleteDept(id));
    }

    /**
     * 编辑部门
     *
     * @param sysDept
     * @return
     */
    @SysLog("编辑部门")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_dept')")
    public Response update(@Valid @RequestBody SysDept sysDept) {
        return Response.success(sysDeptService.updateDept(sysDept));
    }

    /**
     * 补全部门树
     *
     * @return
     */
    @PostMapping(value = "/tree")
    public Response getDeptTree(@RequestBody List<DeptVo> deptVos) {
        return Response.success(sysDeptService.getDeptTree(deptVos));
    }

}
