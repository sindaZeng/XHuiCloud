package com.zsinda.fdp;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringCloudApplication
public class FdpAuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FdpAuthServerApplication.class,args);
    }
}
