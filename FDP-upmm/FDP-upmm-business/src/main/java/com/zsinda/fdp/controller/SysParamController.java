package com.zsinda.fdp.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsinda.fdp.entity.SysParam;
import com.zsinda.fdp.service.SysParamService;
import com.zsinda.fdp.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: FDPlatform
 * @description: SysConfigController
 * @author: Sinda
 * @create: 2020-05-09 12:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/param")
@Api(value = "param", tags = "系统公共参数模块")
public class SysParamController {

    private final SysParamService sysConfigService;

    /**
     * 分页查询系统参数列表
     *
     * @return
     */
    @GetMapping("/page")
    public R page(Page page) {
        return R.ok(sysConfigService.page(page,Wrappers.<SysParam>lambdaQuery().eq(SysParam::getDelFlag, 1)));
    }

}


