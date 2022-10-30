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
import com.xhuicloud.upms.entity.SysRouteConf;
import com.xhuicloud.upms.service.SysRouteConfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: XHuiCloud
 * @description: SysRouteConfController
 * @author: Sinda
 * @create: 2020-03-02 16:28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/route")
@Api(value = "route",tags = "动态路由管理模块")
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
     * @return
     */
    @GetMapping("/listRoutes")
    public Response<List<SysRouteConf>> listRoutes() {
        return Response.success(sysRouteConfService.getRoutes());
    }


}
