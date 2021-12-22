package com.xhuicloud.push.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.push.entity.PushGroup;
import com.xhuicloud.push.service.PushGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/group")
@AllArgsConstructor
@Api(value = "group", tags = "推送模板组管理模块")
public class PushGroupController {

    private final PushGroupService pushGroupService;

    /**
     * 分页查询推送模板组列表
     *
     * @param page
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询推送模板组列表", notes = "分页查询推送模板组列表")
    public Response page(Page page) {
        return Response.success(pushGroupService.page(page));
    }

    /**
     * 新增推送模板组
     *
     * @param pushGroup
     * @return
     */
    @SysLog("新增推送模板组")
    @PostMapping
    @CacheEvict(value = CacheConstants.PUSH_GROUP, allEntries = true)
    @ApiOperation(value = "新增推送模板组", notes = "新增推送模板组")
    public Response save(@Valid @RequestBody PushGroup pushGroup) {
        return Response.success(pushGroupService.save(pushGroup));
    }

    /**
     * 更新推送模板组
     *
     * @param pushGroup
     * @return
     */
    @SysLog("编辑推送模板组")
    @PutMapping
    @CacheEvict(value = CacheConstants.PUSH_GROUP, allEntries = true)
    @ApiOperation(value = "编辑推送模板组", notes = "编辑推送模板组")
    public Response update(@Valid @RequestBody PushGroup pushGroup) {
        return Response.success(pushGroupService.updateById(pushGroup));
    }

    /**
     * 删除推送模板组
     *
     * @param id
     * @return
     */
    @SysLog("删除推送模板组")
    @DeleteMapping("/{id}")
    @CacheEvict(value = CacheConstants.PUSH_GROUP, allEntries = true)
    @ApiOperation(value = "删除推送模板组", notes = "删除推送模板组")
    public Response delete(@PathVariable Integer id) {
        return Response.success(pushGroupService.removeById(id));
    }

    /**
     * 通过ID查询推送模板组信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Response getById(@PathVariable Integer id) {
        return Response.success(pushGroupService.getById(id));
    }

}
