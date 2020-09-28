package com.xhuicloud.common.log.init;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @program: XHuiCloud
 * @description: 初始化日志路径
 * @author: Sinda
 * @create: 2020-02-01 01:08
 */
public class ApplicationLoggerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String appName = environment.getProperty("spring.application.name");
        String logBase = environment.getProperty("logging.path", "logs");
//         spring boot admin 直接加载日志
        System.setProperty("logging.file", String.format("%s/%s/debug.log", logBase, appName));

    }
}
