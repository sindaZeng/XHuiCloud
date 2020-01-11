package com.zsinda.fdp.controller;

import com.zsinda.fdp.service.MobileService;
import com.zsinda.fdp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MobileController {
    @Autowired
    private MobileService mobileService;

    @GetMapping("/{mobile}")
    public R sendSmsCode(@PathVariable String mobile) {
        return mobileService.sendSmsCode(mobile);
    }
}
