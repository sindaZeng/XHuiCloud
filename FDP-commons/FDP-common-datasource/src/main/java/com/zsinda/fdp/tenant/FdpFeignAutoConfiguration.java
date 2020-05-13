package com.zsinda.fdp.tenant;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @program: FDPlatform
 * @description: FdpFeignAutoConfiguration
 * @author: Sinda
 * @create: 2020-05-13 10:48
 */
public class FdpFeignAutoConfiguration {

    @Bean
    public RequestInterceptor fdpRequestInterceptor() {
        return new FdpRequestInterceptor();
    }
}
