package com.zsinda.fdp.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @program: FDPlatform
 * @description: 跨域配置
 * @author: Sinda
 * @create: 2020-04-21 20:40:39
 */
@Component
public class CorsOriginFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"POST,GET,PUT,OPTIONS,DELETE,PATCH");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"*");
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,"*");
        return chain.filter(exchange);
    }
}
