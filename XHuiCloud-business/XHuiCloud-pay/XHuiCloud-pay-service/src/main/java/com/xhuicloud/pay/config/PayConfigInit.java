package com.xhuicloud.pay.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.jpay.alipay.AliPayApiConfig;
import com.jpay.alipay.AliPayApiConfigKit;
import com.xhuicloud.common.core.enums.pay.PayTypeEnum;
import com.xhuicloud.pay.entity.PayChannel;
import com.xhuicloud.pay.service.PayChannelService;
import com.xhuicloud.common.datasource.tenant.XHuiTenantHolder;
import com.xhuicloud.upms.entity.SysTenant;
import com.xhuicloud.upms.feign.SysTenantServiceFeign;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_INNER_YES;

/**
 * @program: XHuiCloud
 * @description: 支付方式初始化
 * @author: Sinda
 * @create: 2020-06-03 11:53
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class PayConfigInit {

    private final SysTenantServiceFeign sysTenantServiceFeign;

    private final PayChannelService payChannelService;

    public final static Map<Integer,String> tenantIdAliPayAppIdMaps = Maps.newHashMap();

    public final static Map<Integer, SysTenant> tenantMaps = Maps.newHashMap();

    @Async
    @Order
    @EventListener(WebServerInitializedEvent.class)
    public void init() {

        List<PayChannel> payChannels = new ArrayList<>();

        sysTenantServiceFeign.list(IS_COMMING_INNER_YES).getData().forEach(tenant -> {
            XHuiTenantHolder.setTenant(tenant.getId());

            List<PayChannel> payChannelList = payChannelService
                    .list(Wrappers.<PayChannel>lambdaQuery()
                            .eq(PayChannel::getDelFlag, 1)
                            .eq(PayChannel::getTenantId, tenant.getId()));
            payChannels.addAll(payChannelList);
            tenantMaps.put(tenant.getId(),tenant);
        });

        payChannels.forEach(payChannel -> {
            tenantIdAliPayAppIdMaps.put(payChannel.getTenantId(),payChannel.getAppId());
        });

        payChannels.forEach(payChannel -> {
            JSONObject params = JSONUtil.parseObj(payChannel.getConfig());
            if (StringUtils.equals(payChannel.getChannelId(), (PayTypeEnum.ALIPAY_WAP.getType()))) {
                AliPayApiConfig aliPayApiConfig = AliPayApiConfig.New()
                        .setAppId(payChannel.getAppId())
                        .setPrivateKey(params.getStr("privateKey"))
                        .setCharset(CharsetUtil.UTF_8)
                        .setAlipayPublicKey(params.getStr("alipayPublicKey"))
                        .setServiceUrl(params.getStr("serviceUrl"))
                        .setSignType("RSA2")
                        .build();
                AliPayApiConfigKit.putApiConfig(aliPayApiConfig);
                log.info("AliPay支付渠道初始化完成:AppId:{}",payChannel.getAppId());
            }
        });
    }

}
