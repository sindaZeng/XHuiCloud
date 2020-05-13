package com.zsinda.fdp.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zsinda.fdp.annotation.Inner;
import com.zsinda.fdp.entity.SysTenant;
import com.zsinda.fdp.service.SysTenantService;
import com.zsinda.fdp.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: FDPlatform
 * @description: SysTenantController
 * @author: Sinda
 * @create: 2020-05-13 15:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
@Api(value = "tenant", tags = "系统租户模块")
public class SysTenantController {

    private final SysTenantService sysTenantService;

    /**
     * 查询系统租户列表
     *
     * @return
     */
    @Inner(value = false)
    @GetMapping("/list")
    public R list() {
        return R.ok(sysTenantService.list(Wrappers.<SysTenant>lambdaQuery().eq(SysTenant::getDelFlag, 1).eq(SysTenant::getState,1)));
    }

}


