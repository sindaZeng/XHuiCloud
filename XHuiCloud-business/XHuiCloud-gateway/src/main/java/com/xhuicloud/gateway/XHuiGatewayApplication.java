package com.xhuicloud.gateway;

import com.xhuicloud.common.gateway.annotation.EnableDynamicRoute;
import com.xhuicloud.common.swagger.annotation.EnableXHuiSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDynamicRoute
@EnableXHuiSwagger
@EnableDiscoveryClient
public class XHuiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(XHuiGatewayApplication.class,args);
    }

}
