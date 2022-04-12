///*
// * MIT License
// * Copyright <2021-2022>
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
// * of the Software, and to permit persons to whom the Software is furnished to do so,
// * subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in all
// * copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
// * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
// * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
// * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
// * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
// * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
// * @Author: Sinda
// * @Email:  xhuicloud@163.com
// */
//
//package com.xhuicloud.upms.controller;
//
//import com.xhuicloud.common.core.utils.Response;
//import com.xhuicloud.common.security.annotation.Anonymous;
//import com.xhuicloud.upms.service.SysMobileService;
//import com.xxl.job.admin.api.entity.XxlJobInfo;
//import com.xxl.job.admin.api.feign.JobInfoFeign;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @program: XHuiCloud
// * @description: SysMobileController
// * @author: Sinda
// * @create: 2019-12-26 23:16
// **/
//@RestController
//@RequestMapping("/mobile")
//@AllArgsConstructor
//@Api(value = "mobile",tags = "手机管理模块")
//public class SysMobileController {
//
//    private final SysMobileService mobileService;
//
//    private final JobInfoFeign jobInfoFeign;
//
//    @Anonymous(false)
//    @GetMapping("/{mobile}")
//    public Response sendSmsCode(@PathVariable String mobile) {
//        return mobileService.sendSmsCode(mobile);
//    }
//
//    @Anonymous(false)
//    @GetMapping("/timing/{mobile}")
//    @ApiOperation(value = "定时发送短信", notes = "分页查询字典列表")
//    public Response timingSendSms(@PathVariable String mobile, XxlJobInfo xxlJobInfo) {
//        String add = jobInfoFeign.add("XXL_JOB_LOGIN_IDENTITY=XXXXXXXX", xxlJobInfo);
//        return Response.success();
//    }
//}
