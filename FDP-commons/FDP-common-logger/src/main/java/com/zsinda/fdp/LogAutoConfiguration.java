package com.zsinda.fdp;

import com.zsinda.fdp.aspect.SysLogAspect;
import com.zsinda.fdp.event.SysLogListener;
import com.zsinda.fdp.feign.SysLogServiceFeign;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @program: FDPlatform
 * @description: LogAutoConfiguration
 * @author: Sinda
 * @create: 2020-02-01 01:05
 */
@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "Fdp.log.enabled", havingValue = "true", matchIfMissing = true)
public class LogAutoConfiguration {
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
