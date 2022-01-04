package com.xhuicloud.common.core.thread;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(AsyncThreadExecuteProperties.class)
@ConditionalOnClass(AsyncThreadExecuteProperties.class)
@ConditionalOnProperty(prefix = "xhui.thread", value = "enabled", matchIfMissing = true)
public class AsyncThreadExecuteConfiguration {

    @Bean
    public AsyncThreadExecutePool asyncThreadExecutePool(AsyncThreadExecuteProperties properties) {
        return new AsyncThreadExecutePool(properties);
    }
}
