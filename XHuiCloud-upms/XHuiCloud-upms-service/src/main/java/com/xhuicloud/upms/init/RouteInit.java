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
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.gateway.support.DynamicRouteInitEvent;
import com.xhuicloud.common.gateway.vo.RouteDefinitionVo;
import com.xhuicloud.upms.entity.SysRouteConf;
import com.xhuicloud.upms.service.SysRouteConfService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.URI;
import java.util.List;

/**
 * @program: XHuiCloud
 * @description: RouteInit
 * @author: Sinda
 * @create: 2020-03-02 16:46
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class RouteInit {

    private final RedisTemplate redisTemplate;

    private final SysRouteConfService sysRouteConfService;

    @Async
    @Order
    @EventListener({WebServerInitializedEvent.class, DynamicRouteInitEvent.class})
    public void init() {
        log.info("初始化网关路由开始 ");
        redisTemplate.delete(CommonConstants.ROUTE_KEY);

        List<SysRouteConf> routes = sysRouteConfService.getRoutes();
        if (CollectionUtil.isNotEmpty(routes)) {
            routes.forEach(route -> {
                RouteDefinitionVo vo = new RouteDefinitionVo();
                vo.setRouteName(route.getRouteName());
                vo.setId(route.getRouteId());
                vo.setUri(URI.create(route.getUri()));
                vo.setOrder(route.getSort());

                JSONArray filterObj = JSONUtil.parseArray(route.getFilters());
                vo.setFilters(filterObj.toList(FilterDefinition.class));
                JSONArray predicateObj = JSONUtil.parseArray(route.getPredicates());
                vo.setPredicates(predicateObj.toList(PredicateDefinition.class));

                log.info("加载路由ID：{},{}", route.getRouteId(), vo);
                redisTemplate.setKeySerializer(new StringRedisSerializer());
                redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RouteDefinitionVo.class));
                redisTemplate.opsForHash().put(CommonConstants.ROUTE_KEY, route.getRouteId(), vo);
            });
            log.debug("初始化网关路由结束 ");
        }else {
            throw new NullPointerException("获取初始化网关路由异常!");
        }
        redisTemplate.convertAndSend(CommonConstants.GATEWAY_JVM_ROUTE_RELOAD, "路由信息更新");
    }

    /**
     * redis 监听配置,监听 gateway_redis_route_reload_topic,重新加载Redis
     *
     * @param redisConnectionFactory redis 配置
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener((message, bytes) -> {
            log.warn("接收到重新Redis 重新加载路由事件");
            init();
        }, new ChannelTopic(CommonConstants.ROUTE_RELOAD));
        return container;
    }
}
