package com.zsinda.fdp.controller;

import com.jpay.alipay.AliPayApi;
import com.zsinda.fdp.annotation.Inner;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @program: FDPlatform
 * @description: PayNotifyRecordController
 * @author: Sinda
 * @create: 2020-06-05 11:00
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/notify")
@Api(value = "notify", tags = "异步回调管理")
public class PayNotifyRecordController {

    /**
     * 支付宝渠道异步回调
     *
     * @param request 渠道请求
     * @return
     */
    @Inner(false)
    @SneakyThrows
    @PostMapping("/alipay/callback")
    public void aliCallbak(HttpServletRequest request, HttpServletResponse response) {
        // 解析回调信息
        Map<String, String> params = AliPayApi.toMap(request);
        log.info("回调信息:{}",params);
        response.getWriter().print("success");
    }
}
