package com.xhuicloud.gateway;

import com.xhuicloud.common.gateway.annotation.EnableDynamicRoute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringCloudApplication
@EnableDynamicRoute
public class XHuiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(XHuiGatewayApplication.class,args);
    }

}
