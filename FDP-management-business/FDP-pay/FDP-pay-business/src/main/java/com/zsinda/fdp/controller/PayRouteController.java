package com.zsinda.fdp.controller;

import cn.hutool.core.util.ObjectUtil;
import com.zsinda.fdp.annotation.Inner;
import com.zsinda.fdp.config.PayConfigInit;
import com.zsinda.fdp.constant.CommonConstants;
import com.zsinda.fdp.dto.PayOrderDto;
import com.zsinda.fdp.entity.SysTenant;
import com.zsinda.fdp.enums.pay.PayTypeEnum;
import com.zsinda.fdp.exception.SysException;
import com.zsinda.fdp.handle.impl.AliPayServiceImpl;
import com.zsinda.fdp.properties.PayProperties;
import com.zsinda.fdp.tenant.FdpTenantHolder;
import com.zsinda.fdp.utils.UserAgentUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: FDPlatform
 * @description: 支付中心路由
 * @author: Sinda
 * @create: 2020-06-04 14:31
 */
@Controller
@RequestMapping("/route")
@AllArgsConstructor
@Api(value = "route", tags = "支付路由模块")
public class PayRouteController {

    private final AliPayServiceImpl aliPayService;

    private final PayProperties payProperties;

    /**
     * 聚合支付 被扫
     *
     * @param modelAndView
     * @param request
     * @return
     */
    @SneakyThrows
    @GetMapping
    @Inner(value = false)
    public ModelAndView toPay(ModelAndView modelAndView,
                              HttpServletRequest request) {
        SysTenant sysTenant = getTenant(FdpTenantHolder.getTenant());
        if (ObjectUtil.isNotNull(sysTenant)) {
            modelAndView.setViewName("ftl/h5pay");
            modelAndView.addObject("tenant", sysTenant);
            if (UserAgentUtil.isWeChat(request)) {
                // 微信
                modelAndView.addObject("channel", PayTypeEnum.WEIXIN_WAP.toString());
            } else {
                // 支付宝
//                String code = request.getParameter("auth_code");
//                if (StringUtils.isEmpty(code)) {
                // 先去获取用户信息 再回来 防止不在对应的客服端打开 后续回调唤起渠道支付带上做处理
//                    modelAndView.setViewName("redirect:https://openauth.alipaydev.com/oauth2/publicAppAuthorize.htm?app_id=" +
//                            PayConfigInit.tenantIdAliPayAppIdMaps.get(FdpTenantHolder.getTenant()) +
//                            "&scope=auth_base&redirect_uri=" +
//                            URLEncoder.encode(payProperties.getDomain(), "utf-8") +
//                            "pay%2froute%3ftenant_id%3d" + FdpTenantHolder.getTenant());
//                } else {
                modelAndView.addObject("channel", PayTypeEnum.ALIPAY_WAP.toString());
//                    modelAndView.addObject("code", code);
//                }
            }
        }
        return modelAndView;
    }

    /**
     * 唤起渠道支付
     *
     * @param payOrderDto  付款实体
     * @param request
     * @param modelAndView
     * @param modelAndView
     * @return
     */
    @SneakyThrows
    @GetMapping("/call")
    @Inner(value = false)
    public ModelAndView call(PayOrderDto payOrderDto,
                             HttpServletRequest request,
                             ModelAndView modelAndView) {
        payOrderDto.setQuitUrl(payProperties.getDomain() + "pay/route");
        if (ObjectUtil.isNotNull(getTenant(FdpTenantHolder.getTenant()))) {
            if (UserAgentUtil.isWeChat(request)) {
                // 唤起微信
                modelAndView.setViewName("ftl/success");
            } else {
                payOrderDto.setGoodsTitle(CommonConstants.SCAN_CODE_PAY);
                payOrderDto.setChannelId(PayTypeEnum.ALIPAY_WAP.getType());
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
    @Cacheable(value = "Tenant", key = "#tenantId")
    public SysTenant getTenant(Integer tenantId) {
        SysTenant sysTenant = PayConfigInit.tenantMaps.get(tenantId);
        if (ObjectUtil.isNotNull(sysTenant)) {
            FdpTenantHolder.setTenant(Integer.valueOf(tenantId));
        } else {
            throw SysException.sysFail(SysException.TENANT_NOT_EXIST_DATA_EXCEPTION);
        }
        return sysTenant;
    }

}
