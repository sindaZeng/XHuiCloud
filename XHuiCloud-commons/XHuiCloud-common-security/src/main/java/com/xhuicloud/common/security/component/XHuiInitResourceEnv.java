package com.xhuicloud.common.security.component;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class XHuiInitResourceEnv implements EnvironmentPostProcessor, Ordered {

    /**
     * 允许覆写
     * @param environment
     * @param application
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.setProperty("spring.main.allow-bean-definition-overriding", "true");
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
