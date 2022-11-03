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

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.gateway.support.DynamicRouteInitEvent;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.upms.dto.RouteConfDto;
import com.xhuicloud.upms.entity.SysRouteConf;
import com.xhuicloud.upms.service.SysRouteConfService;
import com.xhuicloud.upms.vo.FilterVo;
import com.xhuicloud.upms.vo.PredicateAndFilterVo;
import com.xhuicloud.upms.vo.PredicateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @program: XHuiCloud
 * @description: SysRouteConfController
 * @author: Sinda
 * @create: 2020-03-02 16:28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/route")
@Api(value = "route", tags = "动态路由管理模块")
public class SysRouteConfController {

    private final SysRouteConfService sysRouteConfService;

    /**
     * 分页查询动态路由列表
     *
     * @param page
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询动态路由列表", notes = "分页查询动态路由列表")
    public Response<Page> page(Page page, SysRouteConf sysRouteConf) {
        return Response.success(sysRouteConfService.page(page, Wrappers.query(sysRouteConf)));
    }

    /**
     * 获取所有路由
     *
     * @return
     */
    @GetMapping("/listRoutes")
    public Response<List<SysRouteConf>> listRoutes() {
        return Response.success(sysRouteConfService.getRoutes());
    }

    /**
     * 获取所有路由
     *
     * @return
     */
    @GetMapping("/getPredicatesAndFiltersById/{id}")
    public Response<PredicateAndFilterVo> getPredicatesById(@PathVariable Integer id) {
        PredicateAndFilterVo predicateAndFilterVo = new PredicateAndFilterVo();
        List<PredicateVo> predicateVos = Lists.newArrayList();
        List<FilterVo> filterVos = Lists.newArrayList();
        SysRouteConf sysRouteConf = sysRouteConfService.getById(id);
        String predicates = sysRouteConf.getPredicates();
        if (StrUtil.isNotBlank(predicates)) {
            List<PredicateDefinition> predicateDefinitions = JSONUtil.toList(predicates, PredicateDefinition.class);
            for (PredicateDefinition predicateDefinition : predicateDefinitions) {
                for (Map.Entry<String, String> args : predicateDefinition.getArgs().entrySet()) {
                    PredicateVo predicateVo = new PredicateVo();
                    predicateVo.setName(predicateDefinition.getName());
                    predicateVo.setValue(args.getValue());
                    predicateVos.add(predicateVo);
                }
            }
        }

        String filters = sysRouteConf.getFilters();
        if (StrUtil.isNotBlank(filters)) {
            List<FilterDefinition> filterDefinitions = JSONUtil.toList(filters, FilterDefinition.class);
            for (FilterDefinition filterDefinition : filterDefinitions) {
                for (Map.Entry<String, String> args : filterDefinition.getArgs().entrySet()) {
                    FilterVo filterVo = new FilterVo();
                    filterVo.setName(filterDefinition.getName());
                    filterVo.setValue(args.getValue());
                    filterVos.add(filterVo);
                }
            }
        }
        predicateAndFilterVo.setPredicateVos(predicateVos);
        predicateAndFilterVo.setFilterVos(filterVos);
        return Response.success(predicateAndFilterVo);
    }

    @SysLog("新增动态路由")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_route')")
    @ApiOperation(value = "新增动态路由", notes = "新增动态路由")
    public Response<Boolean> save(@Valid @RequestBody SysRouteConf sysRouteConf) {
        Boolean isSuccess = sysRouteConfService.save(sysRouteConf);
        SpringUtil.publishEvent(new DynamicRouteInitEvent(sysRouteConf));
        return Response.success(isSuccess);
    }

    @SysLog("编辑动态路由")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_route')")
    public Response<Boolean> update(@Valid @RequestBody RouteConfDto routeConfDto) {
        SysRouteConf sysRouteConf = sysRouteConfService.getById(routeConfDto.getId());
        if (sysRouteConf == null) {
            return Response.failed("无效数据!");
        }
        return Response.success(sysRouteConfService.update(sysRouteConf, routeConfDto));
    }

    @SysLog("删除动态路由")
    @PreAuthorize("@authorize.hasPermission('sys_delete_route')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除动态路由", notes = "删除动态路由")
    public Response<Boolean> delete(@PathVariable Integer id) {
        Boolean isSuccess = sysRouteConfService.removeById(id);
        SpringUtil.publishEvent(new DynamicRouteInitEvent(id));
        return Response.success(isSuccess);
    }

}
