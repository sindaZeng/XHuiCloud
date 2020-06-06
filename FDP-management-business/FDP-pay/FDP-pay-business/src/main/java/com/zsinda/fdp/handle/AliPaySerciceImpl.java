package com.zsinda.fdp.handle;

import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.jpay.alipay.AliPayApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
public class AliPaySerciceImpl implements PayService{

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @Override
    public Object pay() {
        AlipayTradeWapPayModel alipayModel = new AlipayTradeWapPayModel();
        alipayModel.setBody("商品描述");
        alipayModel.setSubject("商品的标题/交易标题");
        alipayModel.setOutTradeNo("123123123123123");
        alipayModel.setTimeoutExpress("3m");
        alipayModel.setTotalAmount("0.01");
        alipayModel.setProductCode("123213123123");
        alipayModel.setPassbackParams(String.valueOf(1));
        try {
            AliPayApi.wapPay(response, alipayModel, "http://zsinda.cn:9527"
                    , "http://zsinda.cn:15000/FDP-pay-business/notify/alipay/callback");
        } catch (Exception e) {
            log.error("支付宝手机支付失败", e);
        }
        return null;
    }
}
