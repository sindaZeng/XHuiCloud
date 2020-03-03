package com.zsinda.fdp.controller;

import com.zsinda.fdp.annotation.Inner;
import com.zsinda.fdp.entity.SysRouteConf;
import com.zsinda.fdp.service.SysRouteConfService;
import com.zsinda.fdp.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: FDPlatform
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
     * 获取所有路由
     * @return
     */
    @Inner
    @GetMapping
    public R<List<SysRouteConf>> listRoutes() {
        return R.ok(sysRouteConfService.getRoutes());
    }


}
