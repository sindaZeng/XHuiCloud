package com.xhuicloud.common.gateway.rule;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/7/18 10:39 下午
 */
public interface GlobalGrayLoadBalancer {

    /**
     * 根据服务Id 选择服务
     * @param serviceId
     * @param request
     * @return
     */
    ServiceInstance choose(String serviceId, ServerHttpRequest request);

}
