package com.zsinda.fdp.handle.impl;

import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.jpay.alipay.AliPayApi;
import com.jpay.alipay.AliPayApiConfigKit;
import com.zsinda.fdp.config.PayConfigInit;
import com.zsinda.fdp.constant.CommonConstants;
import com.zsinda.fdp.dto.PayOrderDto;
import com.zsinda.fdp.entity.PayOrderAll;
import com.zsinda.fdp.enums.pay.PayTypeEnum;
import com.zsinda.fdp.handle.PayService;
import com.zsinda.fdp.service.PayOrderAllService;
import com.zsinda.fdp.tenant.FdpTenantHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: FDPlatform
 * @description: AliPaySerciceImpl
 * @author: Sinda
 * @create: 2020-06-05 10:42
 */
@Slf4j
@Service
@AllArgsConstructor
public class AliPaySerciceImpl implements PayService {

    private final HttpServletResponse response;

    private final PayOrderAllService payOrderAllService;

    @Override
    public Object pay(PayOrderDto payOrderDto) {
        payOrderDto.setGoodsTitle(CommonConstants.SCAN_CODE_PAY);
        payOrderDto.setChannelId(PayTypeEnum.ALIPAY_WAP.getType());
        // 创建本地预交易订单
        PayOrderAll payOrderAll = payOrderAllService.create(payOrderDto);

        openAlipay(payOrderDto, payOrderAll);
        return null;
    }

    private void openAlipay(PayOrderDto payOrderDto, PayOrderAll payOrderAll) {
        try {
            Integer tenantId = FdpTenantHolder.getTenant();
            AlipayTradeWapPayModel aliPayModel = new AlipayTradeWapPayModel();
            aliPayModel.setSubject(payOrderAll.getGoodsTitle());
            aliPayModel.setOutTradeNo(payOrderAll.getOrderNo());
            aliPayModel.setTimeoutExpress(CommonConstants.TIMEOUT_EXPRESS);
            aliPayModel.setTotalAmount(String.valueOf(payOrderDto.getAmount()));
            aliPayModel.setProductCode("QUICK_WAP_WAY"); //手机网站支付 为固定值
            aliPayModel.setPassbackParams("tenant_id=" + tenantId);// 回传租户id
            aliPayModel.setQuitUrl(payOrderDto.getQuitUrl());//用户付款中途退出返回商户网站的地址
            // 根据租户 选择支付商户号
            AliPayApiConfigKit.setThreadLocalAppId(PayConfigInit.tenantIdAliPayAppIdMaps.get(tenantId));
            AliPayApi.wapPay(response, aliPayModel, ""
                    , "http://zsinda.cn:15000/pay/notify/alipay/callback");
        } catch (Exception e) {
            log.error("支付宝手机支付失败", e);
        }
    }
}
