package com.xhuicloud.common.gray;

import com.xhuicloud.common.gray.rule.GlobalGrayRibbonLoadBalancerRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/7/18 9:56 下午
 */
public class GlobalGrayRibbonLoadBalancerConfiguration extends LoadBalancerClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GlobalGrayRibbonLoadBalancerRule globalGrayRibbonLoadBalancerRule(Environment environment,
                                                                             LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new GlobalGrayRibbonLoadBalancerRule(
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }


}
