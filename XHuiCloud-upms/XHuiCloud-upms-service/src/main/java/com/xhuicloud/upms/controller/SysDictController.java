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
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.AuditRecord;
import com.xhuicloud.upms.entity.SysDict;
import com.xhuicloud.upms.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/dict")
@AllArgsConstructor
@Api(value = "dict", tags = "字典项模块")
public class SysDictController {

    private final SysDictService sysDictService;

    /**
     * 分页查询字典列表
     *
     * @param page
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询字典列表", notes = "分页查询字典列表")
    public Response<Page> page(Page page, SysDict sysDict) {
        return Response.success(sysDictService.page(page, Wrappers.lambdaQuery(sysDict)));
    }

    /**
     * 新增字典项
     *
     * @param sysDict
     * @return
     */
    @AuditRecord("新增字典项")
    @PostMapping
    @CacheEvict(value = CacheConstants.SYS_DICT, allEntries = true)
    @ApiOperation(value = "新增字典项", notes = "新增字典项")
    @PreAuthorize("@authorize.hasPermission('sys_add_dict')")
    public Response<Boolean> save(@Valid @RequestBody SysDict sysDict) {
        return Response.success(sysDictService.save(sysDict));
    }

    @AuditRecord("编辑字典项")
    @PutMapping
    @CacheEvict(value = CacheConstants.SYS_DICT, allEntries = true)
    @ApiOperation(value = "编辑字典项", notes = "编辑字典项")
    @PreAuthorize("@authorize.hasPermission('sys_editor_dict')")
    public Response<Boolean> update(@Valid @RequestBody SysDict sysDict) {
        return Response.success(sysDictService.updateById(sysDict));
    }

    /**
     * 删除字典项
     *
     * @param id
     * @return
     */
    @AuditRecord("删除字典项")
    @DeleteMapping("/{id}")
    @CacheEvict(value = CacheConstants.SYS_DICT, allEntries = true)
    @ApiOperation(value = "删除字典项", notes = "删除字典项")
    @PreAuthorize("@authorize.hasPermission('sys_delete_dict')")
    public Response<Boolean> delete(@PathVariable Long id) {
        return Response.success(sysDictService.removeById(id));
    }

}
