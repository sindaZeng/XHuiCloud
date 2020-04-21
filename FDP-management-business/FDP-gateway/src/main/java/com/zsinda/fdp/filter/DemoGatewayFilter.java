package com.zsinda.fdp.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

/**
 * @program: FDPlatform
 * @description: DemoGatewayFilter
 * @author: Sinda
 * @create: 2020-04-21 11:38
 */
@Slf4j
@Component
public class DemoGatewayFilter extends AbstractGatewayFilterFactory {
    private static final String TOKEN = "token";

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (StrUtil.equalsIgnoreCase(request.getMethodValue(), HttpMethod.GET.name()) ||
                    StrUtil.containsIgnoreCase(request.getURI().getPath(), TOKEN)) {
                return chain.filter(exchange);
            }
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.LOCKED);
            return response.setComplete();
        };
    }
}
