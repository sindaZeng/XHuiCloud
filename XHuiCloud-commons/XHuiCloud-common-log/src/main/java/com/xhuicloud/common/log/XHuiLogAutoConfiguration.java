package com.xhuicloud.common.log;

import com.xhuicloud.common.log.aspect.SysLogAspect;
import com.xhuicloud.common.log.event.SysLogListener;
import com.xhuicloud.logs.feign.SysLogServiceFeign;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @program: XHuiCloud
 * @description: LogAutoConfiguration
 * @author: Sinda
 * @create: 2020-02-01 01:05
 */
@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "xhui.log.enabled", havingValue = "true", matchIfMissing = true)
public class XHuiLogAutoConfiguration {
    private final SysLogServiceFeign remoteLogService;

    @Bean
    public SysLogListener sysLogListener() {
        return new SysLogListener(remoteLogService);
    }

    @Bean
    public SysLogAspect sysLogAspect(ApplicationEventPublisher publisher) {
        return new SysLogAspect(publisher);
    }
}
