package com.xhuicloud.common.mybatis.tenant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/9/27 11:10 上午
 */
@Configuration
public class XHuiTenantConfiguration {

    @Bean
    public ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
        return new XHuiRequestInterceptor();
    }

}
