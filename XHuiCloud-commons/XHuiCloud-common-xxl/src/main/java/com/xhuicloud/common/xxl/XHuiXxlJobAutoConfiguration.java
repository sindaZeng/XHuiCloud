package com.xhuicloud.common.xxl;

import com.xhuicloud.common.xxl.properties.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@EnableAutoConfiguration
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(XxlJobProperties.class)
public class XHuiXxlJobAutoConfiguration {

    private static final String XXL_JOB_ADMIN = "xxl-job-admin";
    private static final String XXL = "xxl";

    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor(XxlJobProperties xxlJobProperties, Environment environment,
                                                     DiscoveryClient discoveryClient) {

        XxlJobProperties.XxlExecutorProperties executor = xxlJobProperties.getExecutor();
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        String appName = executor.getAppName();
        if (!StringUtils.hasText(appName)) {
            appName = environment.getProperty("spring.application.name").toLowerCase(Locale.ROOT);
        }

        String serverList = discoveryClient.getServices().stream().filter(s -> s.contains(XXL))
                .flatMap(s -> discoveryClient.getInstances(s).stream()).map(instance -> String
                        .format("http://%s:%s/%s", instance.getHost(), instance.getPort(), XXL_JOB_ADMIN))
                .collect(Collectors.joining(","));

        if (StringUtils.hasText(serverList)) {
            xxlJobSpringExecutor.setAdminAddresses(serverList);
        } else {
            serverList = xxlJobProperties.getAddresses();
            xxlJobSpringExecutor.setAdminAddresses(serverList);
        }
        log.info("注册地址:{},注册执行器名称:{}", serverList, appName);

        xxlJobSpringExecutor.setAppname(appName);
        xxlJobSpringExecutor.setAddress(xxlJobProperties.getExecutor().getAddress());
        xxlJobSpringExecutor.setIp(xxlJobProperties.getExecutor().getIp());
        xxlJobSpringExecutor.setPort(xxlJobProperties.getExecutor().getPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getExecutor().getAccessToken());
        xxlJobSpringExecutor.setLogPath(xxlJobProperties.getExecutor().getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getExecutor().getLogRetentionDays());
        return xxlJobSpringExecutor;
    }

}
