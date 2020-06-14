package com.zsinda.fdp.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.jpay.alipay.AliPayApi;
import com.jpay.alipay.AliPayApiConfig;
import com.jpay.alipay.AliPayApiConfigKit;
import com.jpay.util.StringUtils;
import com.jpay.vo.AjaxResult;
import com.zsinda.fdp.annotation.Inner;
import com.zsinda.fdp.config.PayConfigInit;
import com.zsinda.fdp.constant.ServiceNameConstants;
import com.zsinda.fdp.constant.SysConfigConstants;
import com.zsinda.fdp.dto.PayOrderDto;
import com.zsinda.fdp.entity.SysTenant;
import com.zsinda.fdp.enums.pay.PayTypeEnum;
import com.zsinda.fdp.exception.SysException;
import com.zsinda.fdp.feign.SysParamServiceFeign;
import com.zsinda.fdp.handle.impl.AliPaySerciceImpl;
import com.zsinda.fdp.tenant.FdpTenantHolder;
import com.zsinda.fdp.util.PayKit;
import com.zsinda.fdp.util.RsaKit;
import com.zsinda.fdp.utils.UserAgentUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.zsinda.fdp.constant.AuthorizationConstants.IS_COMMING_INNER_YES;

/**
 * @program: FDPlatform
 * @description: 支付中心路由
 * @author: Sinda
 * @create: 2020-06-04 14:31
 */
@RequestMapping("/route")
@AllArgsConstructor
@Api(value = "route", tags = "支付路由模块")
public class PayRouteController {

    private final AliPaySerciceImpl aliPayService;

    private final SysParamServiceFeign sysParamServiceFeign;

    /**
     * 聚合支付 被扫
     *
     * @param modelAndView
     * @param request
     * @return
     */
    @GetMapping
    @Inner(value = false)
    public ModelAndView toPay(ModelAndView modelAndView,
                              HttpServletRequest request) {
        SysTenant sysTenant = getTenant(FdpTenantHolder.getTenant());
        if (ObjectUtil.isNotNull(sysTenant)) {
            modelAndView.setViewName("ftl/h5pay");
            modelAndView.addObject("tenant", sysTenant);
            if (UserAgentUtil.isWeChat(request)) {
                // 微信主题
                modelAndView.addObject("channel", PayTypeEnum.WEIXIN_WAP.toString());
            } else {
                // 支付宝主题
                modelAndView.addObject("channel", PayTypeEnum.ALIPAY_WAP.toString());
            }
        }
        return modelAndView;
    }

    /**
     * 唤起渠道支付
     *
     * @param modelAndView
     * @return
     */
    @GetMapping("/call")
    @Inner(value = false)
    public ModelAndView call(PayOrderDto payOrderDto,
                             HttpServletRequest request,
                             ModelAndView modelAndView) {
        payOrderDto.setQuitUrl(sysParamServiceFeign.get(SysConfigConstants.SYS_DEFAULT_DOMAIN,
                IS_COMMING_INNER_YES).getData().getParamValue()
                + ServiceNameConstants.FDP_PAY_BUSINESS + "/route");
        if (ObjectUtil.isNotNull(getTenant(FdpTenantHolder.getTenant()))) {
            if (UserAgentUtil.isWeChat(request)) {
                // 唤起微信
                modelAndView.setViewName("ftl/success");
            } else {
                // 唤起支付宝
                aliPayService.pay(payOrderDto);
            }
        }
        return modelAndView;
    }

    /**
     * 获取租户明细
     *
     * @param tenantId
     * @return
     */
    private SysTenant getTenant(Integer tenantId) {
        SysTenant sysTenant = PayConfigInit.tenantMaps.get(tenantId);
        if (ObjectUtil.isNotNull(sysTenant)) {
            FdpTenantHolder.setTenant(Integer.valueOf(tenantId));
        } else {
            throw SysException.sysFail(SysException.TENANT_NOT_EXIST_DATA_EXCEPTION);
        }
        return sysTenant;
    }

    /**
     * app支付
     */
    @RequestMapping(value = "/appPay")
    @ResponseBody
    @Inner(value = false)
    public AjaxResult appPay() {
        AjaxResult result = new AjaxResult();
        try {
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody("我是测试数据-By Javen");
            model.setSubject("App支付测试-By Javen");
            model.setOutTradeNo(StringUtils.getOutTradeNo());
            model.setTimeoutExpress("30m");
            model.setTotalAmount("0.01");
            model.setPassbackParams("callback params");
            model.setProductCode("QUICK_MSECURITY_PAY");
            String orderInfo = AliPayApi.appPayToResponse(model,  sysParamServiceFeign.get(SysConfigConstants.SYS_DEFAULT_DOMAIN,
                    IS_COMMING_INNER_YES).getData().getParamValue()
                    + ServiceNameConstants.FDP_PAY_BUSINESS + "/route").getBody();
            result.success(orderInfo);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 扫码支付
     */
    @Inner(value = false)
    @RequestMapping(value = "/tradePreCreatePay")
    @ResponseBody
    public String tradePreCreatePay() {
        String subject = "Javen 支付宝扫码支付测试";
        String totalAmount = "86";
        String storeId = "123";
        String notifyUrl = sysParamServiceFeign.get(SysConfigConstants.SYS_DEFAULT_DOMAIN,
                IS_COMMING_INNER_YES).getData().getParamValue()
                + ServiceNameConstants.FDP_PAY_BUSINESS + "/route";

        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        model.setStoreId(storeId);
        model.setTimeoutExpress("5m");
        model.setOutTradeNo(StringUtils.getOutTradeNo());
        try {
            String resultStr = AliPayApi.tradePrecreatePayToResponse(model, notifyUrl).getBody();
            JSONObject jsonObject = JSONObject.parseObject(resultStr);
            return jsonObject.getJSONObject("alipay_trade_precreate_response").getString("qr_code");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/wapPayNoSdk")
    @ResponseBody
    @Inner(value = false)
    public void wapPayNoSdk(HttpServletResponse response) {
        try {
            AliPayApiConfigKit.setThreadLocalAppId(PayConfigInit.tenantIdAliPayAppIdMaps.get(FdpTenantHolder.getTenant()));
            AliPayApiConfig aliPayApiConfig = AliPayApiConfigKit.getAliPayApiConfig();
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("app_id", aliPayApiConfig.getAppId());
            paramsMap.put("method", "alipay.trade.wap.pay");
            paramsMap.put("return_url", sysParamServiceFeign.get(SysConfigConstants.SYS_DEFAULT_DOMAIN,
                    IS_COMMING_INNER_YES).getData().getParamValue()
                    + ServiceNameConstants.FDP_PAY_BUSINESS + "/route1");
            paramsMap.put("charset", aliPayApiConfig.getCharset());
            paramsMap.put("sign_type", aliPayApiConfig.getSignType());
            paramsMap.put("timestamp", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            paramsMap.put("version", "1.0");
            paramsMap.put("notify_url", sysParamServiceFeign.get(SysConfigConstants.SYS_DEFAULT_DOMAIN,
                    IS_COMMING_INNER_YES).getData().getParamValue()
                    + ServiceNameConstants.FDP_PAY_BUSINESS + "/route");

            Map<String, String> bizMap = new HashMap<>();
            bizMap.put("body", "IJPay 聚合支付-H5");
            bizMap.put("subject", "IJPay 让支付触手可及");
            bizMap.put("out_trade_no", StringUtils.getOutTradeNo());
            bizMap.put("total_amount", "6.66");
            bizMap.put("product_code", "QUICK_WAP_WAY");

            paramsMap.put("biz_content", JSON.toJSONString(bizMap));

            String content = PayKit.createLinkString(paramsMap);

            System.out.println(content);

            String encrypt = RsaKit.encryptByPrivateKey(content, aliPayApiConfig.getPrivateKey());
            System.out.println(encrypt);
//            encrypt = AlipaySignature.rsaSign(content,aliPayApiConfig.getPrivateKey(), "UTF-8","RSA2");
//            System.out.println(encrypt);
            paramsMap.put("sign", encrypt);

            String url = aliPayApiConfig.getServiceUrl() + "?" + PayKit.createLinkString(paramsMap, true);
            System.out.println(url);
            response.sendRedirect(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
