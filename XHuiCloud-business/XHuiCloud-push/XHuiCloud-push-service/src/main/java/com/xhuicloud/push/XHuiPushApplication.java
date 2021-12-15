package com.xhuicloud.push;

import com.xhuicloud.common.feign.annotation.EnableXHuiFeignClients;
import com.xhuicloud.common.mq.annotation.EnableXHuiRabbitMq;
import com.xhuicloud.common.security.annotation.EnableXHuiResourceServer;
import com.xhuicloud.common.swagger.annotation.EnableXHuiSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableXHuiRabbitMq
@EnableXHuiSwagger
@SpringBootApplication
@EnableDiscoveryClient
@EnableXHuiFeignClients
@EnableXHuiResourceServer
public class XHuiPushApplication {

    public static void main(String[] args) {
        SpringApplication.run(XHuiPushApplication.class, args);
    }
}
