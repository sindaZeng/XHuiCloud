/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.pay.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.jpay.alipay.AliPayApiConfig;
import com.jpay.alipay.AliPayApiConfigKit;
import com.xhuicloud.common.core.enums.pay.PayTypeEnum;
import com.xhuicloud.common.data.ttl.XHuiCommonThreadLocalHolder;
import com.xhuicloud.pay.entity.PayChannel;
import com.xhuicloud.pay.service.PayChannelService;
import com.xhuicloud.upms.feign.SysTenantServiceFeign;
import com.xhuicloud.upms.vo.TenantVo;
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

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_ANONYMOUS_YES;

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

    public final static Map<Integer, TenantVo> tenantMaps = Maps.newHashMap();

    @Async
    @Order
    @EventListener(WebServerInitializedEvent.class)
    public void init() {

        List<PayChannel> payChannels = new ArrayList<>();
        List<TenantVo> data = null;
        while (CollectionUtil.isEmpty(data)) {
            data = sysTenantServiceFeign.list(IS_COMMING_ANONYMOUS_YES).getData();
        }
        data.forEach(tenant -> {
            XHuiCommonThreadLocalHolder.setTenant(tenant.getId());

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
