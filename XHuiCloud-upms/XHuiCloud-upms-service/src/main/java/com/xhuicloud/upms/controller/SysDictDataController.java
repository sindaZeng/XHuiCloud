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
import com.xhuicloud.upms.entity.SysDictData;
import com.xhuicloud.upms.service.SysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/dictData")
@AllArgsConstructor
@Api(value = "dictData", tags = "字典模块")
public class SysDictDataController {

    private final SysDictDataService sysDictDataService;

    /**
     * 分页查询字典列表
     *
     * @param page
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询字典列表", notes = "分页查询字典列表")
    public Response page(SysDictData sysDictData, Page page) {
        return Response.success(sysDictDataService.dictDataPage(sysDictData, page));
    }

    /**
     * 新增字典
     *
     * @param sysDictData
     * @return
     */
    @SysLog("新增字典")
    @PostMapping
    @CacheEvict(value = CacheConstants.SYS_DICT_DATA, allEntries = true)
    @ApiOperation(value = "新增字典", notes = "新增字典")
    public Response save(@Valid @RequestBody SysDictData sysDictData) {
        return Response.success(sysDictDataService.save(sysDictData));
    }

    @SysLog("编辑字典")
    @PutMapping
    @CacheEvict(value = CacheConstants.SYS_DICT_DATA, allEntries = true)
    @ApiOperation(value = "编辑字典", notes = "编辑字典")
    public Response update(@Valid @RequestBody SysDictData sysDictData) {
        return Response.success(sysDictDataService.updateById(sysDictData));
    }

    /**
     * 删除字典
     *
     * @param id
     * @return
     */
    @SysLog("删除字典")
    @DeleteMapping("/{id}")
    @CacheEvict(value = CacheConstants.SYS_DICT_DATA, allEntries = true)
    @ApiOperation(value = "删除字典", notes = "删除字典")
    public Response delete(@PathVariable Integer id) {
        return Response.success(sysDictDataService.removeById(id));
    }
}
