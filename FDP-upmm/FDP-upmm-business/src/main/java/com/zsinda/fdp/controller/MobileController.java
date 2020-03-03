package com.zsinda.fdp.controller;

import com.zsinda.fdp.service.MobileService;
import com.zsinda.fdp.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: FDPlatform
 * @description: MobileController
 * @author: Sinda
 * @create: 2019-12-26 23:16 13333333333
 **/

@RestController
@RequestMapping("/mobile")
@AllArgsConstructor
@Api(value = "mobile",tags = "手机管理模块")
public class MobileController {

    private final MobileService mobileService;

    @GetMapping("/{mobile}")
    public R sendSmsCode(@PathVariable String mobile) {
        return mobileService.sendSmsCode(mobile);
    }
}
