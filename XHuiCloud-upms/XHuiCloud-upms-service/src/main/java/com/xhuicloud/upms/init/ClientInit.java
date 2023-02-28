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

import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.core.utils.BeanUtils;
import com.xhuicloud.common.gateway.support.ClientDetailsInitEvent;
import com.xhuicloud.common.gateway.vo.ClientDefinitionVo;
import com.xhuicloud.upms.service.SysClientDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @program: XHuiCloud
 * @description: ClientInit
 * @author: Sinda
 * @create: 2020-03-02 16:46
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class ClientInit implements InitializingBean {

    private final RedisTemplate redisTemplate;

    private final SysClientDetailsService sysClientDetailsService;

    private final RedisMessageListenerContainer listenerContainer;

    @Async
    @Order
    @EventListener({WebServerInitializedEvent.class})
    public void webServerInit() {
        clientDetailsInit();
    }

    @Async
    @Order
    @TransactionalEventListener({ClientDetailsInitEvent.class})
    public void clientDetailsInit() {
        log.info("初始化客户端信息开始 ");
        sysClientDetailsService.list().forEach(clientDetails -> {
            String key = String.format("%s:%s", CacheConstants.CLIENT_DETAILS_EXTENSION, clientDetails.getClientId());
            ClientDefinitionVo clientDefinitionVo = BeanUtils.copy(clientDetails, ClientDefinitionVo.class);
            redisTemplate.opsForValue().set(key, clientDefinitionVo);
        });
        log.debug("初始化客户端信息结束 ");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        listenerContainer.addMessageListener((message, bytes) -> {
            log.warn("Redis 重新初始化客户端信息事件");
            clientDetailsInit();
        }, new ChannelTopic(CommonConstants.CLIENT_RELOAD));
    }

}
