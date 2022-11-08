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

package com.xhuicloud.wechat.init;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import com.xhuicloud.wechat.entity.WeChatAccount;
import com.xhuicloud.wechat.handle.WeChatMpScanHandle;
import com.xhuicloud.wechat.handle.WeChatMpSubscribeHandle;
import com.xhuicloud.wechat.service.WeChatAccountService;
import lombok.AllArgsConstructor;
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

    private final WeChatAccountService weChatAccountService;

    private final WeChatMpScanHandle weChatMpScanHandle;

    private final WeChatMpSubscribeHandle weChatMpSubscribeHandle;

    private static Map<String, WxMpMessageRouter> routersMap = Maps.newHashMap();

    private static Map<String, Integer> tenantsMap = Maps.newHashMap();

    private static Map<String, WxMpService> wxMpServiceMap = Maps.newHashMap();

    @PostConstruct
    public void init() {
        List<WeChatAccount> weChatAccounts = weChatAccountService.list();
        if (CollectionUtil.isNotEmpty(weChatAccounts)) {
            wxMpServiceMap = weChatAccounts.stream().map(weChatAccount -> {
                WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
                config.setAppId(weChatAccount.getAppId()); // 设置微信公众号的appid
                config.setSecret(weChatAccount.getAppSecret()); // 设置微信公众号的app corpSecret
                config.setToken(weChatAccount.getAppAuthToken()); // 设置微信公众号的token
                config.setAesKey(weChatAccount.getAppDecrypt()); // 设置微信公众号的EncodingAESKey

                WxMpService service = new WxMpServiceImpl();
                service.setWxMpConfigStorage(config);

                // ====================================
                final WxMpMessageRouter router = new WxMpMessageRouter(service);
                // 用户扫码事件
                router.rule().async(false)
                        .msgType(WxConsts.XmlMsgType.EVENT)
                        .event(WxConsts.EventType.SCAN)
                        .handler(this.weChatMpScanHandle).end();

                // 用户关注事件
                router.rule().async(false)
                        .msgType(WxConsts.XmlMsgType.EVENT)
                        .event(WxConsts.EventType.SUBSCRIBE)
                        .handler(this.weChatMpSubscribeHandle).end();

                routersMap.put(weChatAccount.getAppId(), router);
                tenantsMap.put(weChatAccount.getAppId(), weChatAccount.getTenantId());
                return service;
            }).collect(Collectors.toMap(s -> s.getWxMpConfigStorage().getAppId(), a -> a));
        }
    }

    public static Map<String, WxMpMessageRouter> getRoutersMap() {
        return routersMap;
    }

    public static Map<String, WxMpService> getWxMpServiceMap() {
        return wxMpServiceMap;
    }

    public static Map<String, Integer> getTenantsMap() {
        return tenantsMap;
    }

}
