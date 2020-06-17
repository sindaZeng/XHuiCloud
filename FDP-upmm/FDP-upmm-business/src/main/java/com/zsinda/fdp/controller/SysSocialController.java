package com.zsinda.fdp.controller;

import com.zsinda.fdp.annotation.Inner;
import com.zsinda.fdp.dto.UserInfo;
import com.zsinda.fdp.service.SysSocialService;
import com.zsinda.fdp.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: FDPlatform
 * @description: SysSocialController
 * @author: Sinda
 * @create: 2020-06-17 12:31
 */
@RestController
@RequestMapping("/social")
@AllArgsConstructor
@Api(value = "social", tags = "第三方登录模块")
public class SysSocialController {

    private final SysSocialService sysSocialService;

    /**
     * 渠道授权码查询用户
     *
     * @param auth_code
     * @return
     */
    @Inner
    @GetMapping("/{auth_code}")
    public R<UserInfo> getSysUser(@PathVariable String auth_code) {
        return R.ok(sysSocialService.getSysUser(auth_code));
    }

}
