package com.xhuicloud.common.gray;

import com.xhuicloud.common.gray.interceptor.GlobalGrayFeignRequestInterceptor;
import com.xhuicloud.common.gray.rule.GlobalGrayRibbonLoadBalancerRule;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/7/18 9:56 下午
 */
@ConditionalOnProperty(value = "gray.enabled", havingValue = "true")
public class GlobalGrayRibbonLoadBalancerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GlobalGrayRibbonLoadBalancerRule globalGrayRibbonLoadBalancerRule() {
        return new GlobalGrayRibbonLoadBalancerRule();
    }

    @Bean
    public RequestInterceptor globalGrayFeignRequestInterceptor() {
        return new GlobalGrayFeignRequestInterceptor();
    }
}
