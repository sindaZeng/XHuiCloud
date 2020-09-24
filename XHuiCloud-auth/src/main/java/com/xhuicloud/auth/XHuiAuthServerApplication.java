package com.xhuicloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class XHuiAuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(XHuiAuthServerApplication.class,args);
    }
}
