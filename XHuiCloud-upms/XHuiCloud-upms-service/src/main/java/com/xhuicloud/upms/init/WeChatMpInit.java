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

package com.xhuicloud.upms.init;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.xhuicloud.common.authorization.resource.constant.CustomAuthorizationGrantType;
import com.xhuicloud.upms.entity.SysSocial;
import com.xhuicloud.upms.handle.wechat.WeChatMpScanHandler;
import com.xhuicloud.upms.handle.wechat.WeChatMpSubscribeHandler;
import com.xhuicloud.upms.service.SysSocialService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@AllArgsConstructor
public class WeChatMpInit {

    private final SysSocialService sysSocialService;

    private final WeChatMpScanHandler weChatMpScanHandler;

    private final WeChatMpSubscribeHandler weChatMpSubscribeHandler;

    @Getter
    private static Map<String, WxMpMessageRouter> routers = Maps.newHashMap();

    @Getter
    private static final Map<String, Integer> tenants = Maps.newHashMap();

    @Getter
    private static Map<String, WxMpService> wxMpServiceMap = Maps.newHashMap();

    @PostConstruct
    public void init() {
        List<SysSocial> sysSocials = sysSocialService.list(Wrappers.<SysSocial>lambdaQuery().eq(SysSocial::getType, CustomAuthorizationGrantType.WECHAT_MP.getValue()));
        if (CollectionUtil.isNotEmpty(sysSocials)) {
            wxMpServiceMap = sysSocials.stream().map(sysSocial -> {
                WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
                config.setAppId(sysSocial.getAppId()); // 设置微信公众号的appid
                config.setSecret(sysSocial.getAppSecret()); // 设置微信公众号的app corpSecret
                config.setToken(sysSocial.getAppAuthToken()); // 设置微信公众号的token
                config.setAesKey(sysSocial.getAppDecrypt()); // 设置微信公众号的EncodingAESKey

                WxMpService service = new WxMpServiceImpl();
                service.setWxMpConfigStorage(config);

                // ====================================
                final WxMpMessageRouter router = new WxMpMessageRouter(service);
                // 用户扫码事件
                router.rule().async(false)
                        .msgType(WxConsts.XmlMsgType.EVENT)
                        .event(WxConsts.EventType.SCAN)
                        .handler(this.weChatMpScanHandler).end();

                // 用户关注事件
                router.rule().async(false)
                        .msgType(WxConsts.XmlMsgType.EVENT)
                        .event(WxConsts.EventType.SUBSCRIBE)
                        .handler(this.weChatMpSubscribeHandler).end();

                routers.put(sysSocial.getAppId(), router);
                tenants.put(sysSocial.getAppId(), sysSocial.getTenantId());
                return service;
            }).collect(Collectors.toMap(s -> s.getWxMpConfigStorage().getAppId(), a -> a));
        }

    }
}
