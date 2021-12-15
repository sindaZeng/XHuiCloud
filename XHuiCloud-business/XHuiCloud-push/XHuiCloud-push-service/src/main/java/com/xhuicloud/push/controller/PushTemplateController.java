package com.xhuicloud.push.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.push.entity.PushTemplate;
import com.xhuicloud.push.service.PushTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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
    public Response page(Page page) {
        return Response.success(pushTemplateService.page(page));
    }

    /**
     * 新增推送模板
     *
     * @param pushTemplate
     * @return
     */
    @SysLog("新增推送模板")
    @PostMapping
    @CacheEvict(value = CacheConstants.PUSH_TEMPLATE, allEntries = true)
    @ApiOperation(value = "新增推送模板", notes = "新增推送模板")
    public Response save(@Valid @RequestBody PushTemplate pushTemplate) {
        return Response.success(pushTemplateService.save(pushTemplate));
    }

    /**
     * 更新推送模板
     *
     * @param pushTemplate
     * @return
     */
    @SysLog("编辑推送模板")
    @PutMapping
    @CacheEvict(value = CacheConstants.PUSH_TEMPLATE, allEntries = true)
    @ApiOperation(value = "编辑推送模板", notes = "编辑推送模板")
    public Response update(@Valid @RequestBody PushTemplate pushTemplate) {
        return Response.success(pushTemplateService.updateById(pushTemplate));
    }

    /**
     * 删除推送模板
     *
     * @param id
     * @return
     */
    @SysLog("删除推送模板")
    @DeleteMapping("/{id}")
    @CacheEvict(value = CacheConstants.PUSH_TEMPLATE, allEntries = true)
    @ApiOperation(value = "删除推送模板", notes = "删除推送模板")
    public Response delete(@PathVariable Integer id) {
        return Response.success(pushTemplateService.removeById(id));
    }

    /**
     * 通过ID查询推送模板信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Response getById(@PathVariable Integer id) {
        return Response.success(pushTemplateService.getById(id));
    }

}
