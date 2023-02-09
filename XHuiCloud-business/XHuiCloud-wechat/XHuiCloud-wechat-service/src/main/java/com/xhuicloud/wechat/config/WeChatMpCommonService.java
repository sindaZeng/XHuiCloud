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

package com.xhuicloud.wechat.config;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.wechat.entity.WeChatAccount;
import com.xhuicloud.wechat.handle.WeChatMpMenuClickHandler;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@AllArgsConstructor
public class WeChatMpCommonService {

    private final WeChatAccountService weChatAccountService;

    private final WeChatMpScanHandle weChatMpScanHandle;

    private final WeChatMpSubscribeHandle weChatMpSubscribeHandle;

    private final WeChatMpMenuClickHandler weChatMpMenuClickHandler;

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
                // 添加事件规则
                addEventRule(router);

                routersMap.put(weChatAccount.getAppId(), router);
                tenantsMap.put(weChatAccount.getAppId(), weChatAccount.getTenantId());
                return service;
            }).collect(Collectors.toMap(s -> s.getWxMpConfigStorage().getAppId(), a -> a));
        }
    }

    private void addEventRule(WxMpMessageRouter router) {
        // 用户扫码事件
        router.rule().async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SCAN)
                .handler(weChatMpScanHandle).end();

        // 用户关注事件
        router.rule().async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE)
                .handler(weChatMpSubscribeHandle).end();

        // 自定义菜单点击事件
        router.rule().async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.CLICK)
                .handler(weChatMpMenuClickHandler).end();
    }

    public static Map<String, WxMpMessageRouter> getRoutersMap() {
        return routersMap;
    }

    public static WxMpMessageRouter getRouters(String appId) {
        WxMpMessageRouter wxMpMessageRouter = routersMap.get(appId);
        if (wxMpMessageRouter == null) {
            SysException.sysFail("没有这个公众号数据!");
        }
        return wxMpMessageRouter;
    }

    public static Map<String, WxMpService> getWxMpServiceMap() {
        return wxMpServiceMap;
    }

    public static WxMpService getWxMpService(String appId) {
        WxMpService wxMpService = wxMpServiceMap.get(appId);
        if (wxMpService == null) {
            SysException.sysFail("没有这个公众号数据!");
        }
        return wxMpService;
    }

    public static Map<String, Integer> getTenantsMap() {
        return tenantsMap;
    }

    public static Integer getTenant(String appId) {
        Integer tenant = tenantsMap.get(appId);
        if (tenant == null) {
            SysException.sysFail("没有这个公众号数据!");
        }
        return tenant;
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener((message, bytes) -> {
            log.warn("重新加载公众号配置事件");
            init();
        }, new ChannelTopic(CommonConstants.WECHAT_CLIENT_RELOAD));
        return container;
    }

}
