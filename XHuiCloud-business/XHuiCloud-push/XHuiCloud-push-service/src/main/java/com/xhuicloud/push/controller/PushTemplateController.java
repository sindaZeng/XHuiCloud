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

package com.xhuicloud.push.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.AuditRecord;
import com.xhuicloud.push.entity.PushTemplate;
import com.xhuicloud.push.service.PushTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/template")
@AllArgsConstructor
@Api(value = "template", tags = "推送模板管理模块")
public class PushTemplateController {

    private final PushTemplateService pushTemplateService;

    /**
     * 分页查询推送模板列表
     *
     * @param page
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询推送模板列表", notes = "分页查询推送模板列表")
    public Response<Page> page(Page page) {
        return Response.success(pushTemplateService.page(page));
    }

    /**
     * 新增推送模板
     *
     * @param pushTemplate
     * @return
     */
    @AuditRecord("新增推送模板")
    @PostMapping
    @ApiOperation(value = "新增推送模板", notes = "新增推送模板")
    public Response<Boolean> save(@Valid @RequestBody PushTemplate pushTemplate) {
        return Response.success(pushTemplateService.save(pushTemplate));
    }

    /**
     * 更新推送模板
     *
     * @param pushTemplate
     * @return
     */
    @AuditRecord("编辑推送模板")
    @PutMapping
    @ApiOperation(value = "编辑推送模板", notes = "编辑推送模板")
    @CacheEvict(value = CacheConstants.PUSH_TEMPLATE, key = "#pushTemplate.id")
    public Response<Boolean> update(@Valid @RequestBody PushTemplate pushTemplate) {
        return Response.success(pushTemplateService.updateById(pushTemplate));
    }

    /**
     * 删除推送模板
     *
     * @param id
     * @return
     */
    @AuditRecord("删除推送模板")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除推送模板", notes = "删除推送模板")
    @CacheEvict(value = CacheConstants.PUSH_TEMPLATE, key = "#pushTemplate.id")
    public Response<Boolean> delete(@PathVariable Integer id) {
        return Response.success(pushTemplateService.removeById(id));
    }

    /**
     * 通过ID查询推送模板信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Cacheable(value = CacheConstants.PUSH_TEMPLATE, key = "#id")
    public Response<PushTemplate> getById(@PathVariable Integer id) {
        return Response.success(pushTemplateService.getById(id));
    }

}
