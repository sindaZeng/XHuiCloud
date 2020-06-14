package com.zsinda.fdp.controller;

import com.jpay.alipay.AliPayApi;
import com.zsinda.fdp.annotation.Inner;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
        log.info("回调信息:{}", params);
        response.getWriter().print("success");
    }

    @Inner(false)
    @RequestMapping(value = "/return_url")
    @ResponseBody
    public String certReturnUrl(HttpServletRequest request) {
        // 获取支付宝GET过来反馈信息
        Map<String, String> map = AliPayApi.toMap(request);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        // TODO 请在这里加上商户的业务逻辑程序代码
        System.out.println("certReturnUrl 验证成功");
        return "success";
    }

    @Inner(false)
    @RequestMapping(value = "/notify_url")
    @ResponseBody
    public String certNotifyUrl(HttpServletRequest request) {
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = AliPayApi.toMap(request);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        // TODO 请在这里加上商户的业务逻辑程序代码 异步通知可能出现订单重复通知 需要做去重处理
        System.out.println("certNotifyUrl 验证成功succcess");
        return "success";
    }

}
