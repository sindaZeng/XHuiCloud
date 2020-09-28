package com.xhuicloud.auth;

import com.xhuicloud.common.feign.annotation.EnableXHuiFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableXHuiFeignClients
@SpringCloudApplication
public class XHuiAuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(XHuiAuthServerApplication.class,args);
    }
}
