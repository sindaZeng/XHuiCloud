package com.xhuicloud.upms.controller;

import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.security.annotation.NoAuth;
import com.xhuicloud.upms.service.SysMobileService;
import com.xxl.job.admin.api.entity.XxlJobInfo;
import com.xxl.job.admin.api.feign.JobInfoFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: XHuiCloud
 * @description: SysMobileController
 * @author: Sinda
 * @create: 2019-12-26 23:16
 **/
@RestController
@RequestMapping("/mobile")
@AllArgsConstructor
@Api(value = "mobile",tags = "手机管理模块")
public class SysMobileController {

    private final SysMobileService mobileService;

    private final JobInfoFeign jobInfoFeign;

    @NoAuth(false)
    @GetMapping("/{mobile}")
    public Response sendSmsCode(@PathVariable String mobile) {
        return mobileService.sendSmsCode(mobile);
    }

    @NoAuth(false)
    @GetMapping("/timing/{mobile}")
    @ApiOperation(value = "定时发送短信", notes = "分页查询字典列表")
    public Response timingSendSms(@PathVariable String mobile, XxlJobInfo xxlJobInfo) {
        String add = jobInfoFeign.add("XXL_JOB_LOGIN_IDENTITY=XXXXXXXX", xxlJobInfo);
        return Response.success();
    }
}
