package com.xhuicloud.upms;

import com.xhuicloud.common.feign.annotation.EnableXHuiFeignClients;
import com.xhuicloud.common.security.annotation.EnableXHuiResourceServer;
import com.xhuicloud.common.swagger.annotation.EnableXHuiSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableXHuiSwagger
@SpringBootApplication
@EnableDiscoveryClient
@EnableXHuiFeignClients
@EnableXHuiResourceServer
public class XHuiUserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(XHuiUserManagementApplication.class, args);
    }
}
