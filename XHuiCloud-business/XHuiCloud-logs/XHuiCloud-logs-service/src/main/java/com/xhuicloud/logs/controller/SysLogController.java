package com.xhuicloud.logs.controller;

import com.xhuicloud.common.security.annotation.Inner;
import com.xhuicloud.logs.entity.SysLog;
import com.xhuicloud.logs.service.SysLogService;
import com.xhuicloud.common.core.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: XHuiCloud
 * @description: SysLogController
 * @author: Sinda
 * @create: 2020-02-01 00:32
 */
@RestController
@RequestMapping("/log")
@AllArgsConstructor
@Api(value = "log", tags = "日志管理模块")
public class SysLogController {

    private final SysLogService sysLogService;

    @Inner
    @PostMapping("save")
    public R save(@RequestBody SysLog sysLog) {
        return R.ok(sysLogService.save(sysLog));
    }

}
