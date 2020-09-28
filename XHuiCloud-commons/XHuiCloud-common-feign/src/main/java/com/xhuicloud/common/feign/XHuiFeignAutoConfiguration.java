package com.xhuicloud.common.feign;

import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.XHuiFeignClientsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/9/25 12:10 下午
 */
@Configuration
@ConditionalOnClass(Feign.class)
@Import(XHuiFeignClientsRegistrar.class)
@AutoConfigureAfter(EnableFeignClients.class)
public class XHuiFeignAutoConfiguration {
}
