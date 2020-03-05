package com.zsinda.fdp;

import com.zsinda.fdp.annotation.EnableDynamicRoute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableDynamicRoute
public class FdpGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FdpGatewayApplication.class,args);
    }

}
