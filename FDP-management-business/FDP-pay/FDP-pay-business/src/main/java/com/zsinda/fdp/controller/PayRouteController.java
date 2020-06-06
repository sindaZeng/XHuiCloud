package com.zsinda.fdp.controller;

import cn.hutool.core.util.ObjectUtil;
import com.zsinda.fdp.annotation.Inner;
import com.zsinda.fdp.constant.CommonConstants;
import com.zsinda.fdp.enums.pay.PayTypeEnum;
import com.zsinda.fdp.feign.SysTenantServiceFeign;
import com.zsinda.fdp.handle.AliPaySerciceImpl;
import com.zsinda.fdp.tenant.FdpTenantHolder;
import com.zsinda.fdp.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

import static com.zsinda.fdp.constant.AuthorizationConstants.IS_COMMING_INNER_YES;

/**
 * @program: FDPlatform
 * @description: RouteController
 * @author: Sinda
 * @create: 2020-06-04 14:31
 */
@RestController
@RequestMapping("/pay")
@AllArgsConstructor
@Api(value = "pay", tags = "支付路由模块")
public class PayRouteController {

    private final SysTenantServiceFeign sysTenantServiceFeign;
    private final AliPaySerciceImpl aliPaySercice;

    @GetMapping
    @Inner(value = false)
    public ModelAndView toPay(@RequestParam(value = "tenant_id") String tenantId,
                              ModelAndView modelAndView,
                              HttpServletRequest request) {
        String UA = request.getHeader(HttpHeaders.USER_AGENT);
        FdpTenantHolder.setTenant(Integer.valueOf(tenantId));
        R r = sysTenantServiceFeign.get(tenantId, IS_COMMING_INNER_YES);
        if (r.isSuccess() && ObjectUtil.isNotNull(r.getData())) {
            modelAndView.setViewName("ftl/h5pay");
            modelAndView.addObject("tenant", r.getData());
            if (UA.contains(CommonConstants.MICRO_MESSENGER)) {
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
     * 创建订单
     *
     * @param modelAndView
     * @return
     */
    @GetMapping("/createOrder")
    @Inner(value = false)
    public ModelAndView createOrder(@NotBlank(message = "租户id不能为空") @RequestParam(value = "tenant_id") String tenantId,
                                    @RequestParam(value = "remark") String remark,
                                    ModelAndView modelAndView) {
        // 微信处理
        modelAndView.setViewName("ftl/success");
        return modelAndView;
    }

}
