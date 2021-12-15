package com.xhuicloud.logs;

import com.xhuicloud.common.feign.annotation.EnableXHuiFeignClients;
import com.xhuicloud.common.security.annotation.EnableXHuiResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableXHuiFeignClients
@EnableXHuiResourceServer
@EnableDiscoveryClient
public class XHuiLogsApplication {

    public static void main(String[] args) {
        SpringApplication.run(XHuiLogsApplication.class, args);
    }
}
