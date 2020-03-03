package com.zsinda.fdp.init;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.zsinda.fdp.constant.CommonConstants;
import com.zsinda.fdp.entity.SysRouteConf;
import com.zsinda.fdp.service.SysRouteConfService;
import com.zsinda.fdp.support.DynamicRouteInitEvent;
import com.zsinda.fdp.vo.RouteDefinitionVo;
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

import java.net.URI;
import java.util.List;

/**
 * @program: FDPlatform
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
        }, new ChannelTopic(CommonConstants.ROUTE_REDIS_RELOAD_TOPIC));
        return container;
    }
}
