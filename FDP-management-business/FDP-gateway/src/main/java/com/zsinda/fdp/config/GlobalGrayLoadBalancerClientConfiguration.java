package com.zsinda.fdp.config;

import com.zsinda.fdp.filter.GlobalGrayReactiveLoadBalancerClientFilter;
import com.zsinda.fdp.rule.GlobalGrayLoadBalancer;
import com.zsinda.fdp.rule.VersionGlobalGrayLoadBalancer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: FDPlatform
 * @description:
 * @author: Sinda
 * @create: 2020/7/18 10:38 下午
 */
@Configuration
@EnableConfigurationProperties(LoadBalancerProperties.class)
@ConditionalOnProperty(value = "gray.enabled", havingValue = "true")
@AutoConfigureBefore(GatewayReactiveLoadBalancerClientAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class GlobalGrayLoadBalancerClientConfiguration {

    @Bean
    public GlobalGrayLoadBalancer globalGrayLoadBalancer(DiscoveryClient discoveryClient) {
        return new VersionGlobalGrayLoadBalancer(discoveryClient);
    }

    @Bean
    public ReactiveLoadBalancerClientFilter gatewayLoadBalancerClientFilter(GlobalGrayLoadBalancer globalGrayLoadBalancer,
                                                                            LoadBalancerProperties properties) {
        return new GlobalGrayReactiveLoadBalancerClientFilter(properties, globalGrayLoadBalancer);
    }

}