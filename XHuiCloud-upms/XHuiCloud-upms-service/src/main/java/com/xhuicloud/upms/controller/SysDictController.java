package com.xhuicloud.upms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.upms.entity.SysDict;
import com.xhuicloud.upms.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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
    public Response page(Page page) {
        return Response.success(sysDictService.page(page));
    }

    /**
     * 新增字典项
     *
     * @param sysDict
     * @return
     */
    @SysLog("新增字典项")
    @PostMapping
    @CacheEvict(value = CacheConstants.SYS_DICT, allEntries = true)
    @ApiOperation(value = "新增字典项", notes = "新增字典项")
    public Response save(@Valid @RequestBody SysDict sysDict) {
        return Response.success(sysDictService.save(sysDict));
    }

    @SysLog("编辑字典项")
    @PutMapping
    @CacheEvict(value = CacheConstants.SYS_DICT, allEntries = true)
    @ApiOperation(value = "编辑字典项", notes = "编辑字典项")
    public Response update(@Valid @RequestBody SysDict sysDict) {
        return Response.success(sysDictService.updateById(sysDict));
    }

    /**
     * 删除字典项
     *
     * @param id
     * @return
     */
    @SysLog("删除字典项")
    @DeleteMapping("/{id}")
    @CacheEvict(value = CacheConstants.SYS_DICT, allEntries = true)
    @ApiOperation(value = "删除字典项", notes = "删除字典项")
    public Response delete(@PathVariable Integer id) {
        return Response.success(sysDictService.removeById(id));
    }

}
