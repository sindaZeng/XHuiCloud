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
@RequestMapping("/dict-data")
@AllArgsConstructor
@Api(value = "dict-data", tags = "字典模块")
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
