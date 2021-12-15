package com.xhuicloud.common.gray;

import com.xhuicloud.common.gray.interceptor.GlobalGrayFeignRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(value = "gray.enabled", havingValue = "true")
@LoadBalancerClients(defaultConfiguration = GlobalGrayRibbonLoadBalancerConfiguration.class)
public class GrayLoadBalancerAutoConfiguration {

    @Bean
    public RequestInterceptor globalGrayFeignRequestInterceptor() {
        return new GlobalGrayFeignRequestInterceptor();
    }

}
