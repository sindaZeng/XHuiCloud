package com.xhuicloud.upms;

import com.xhuicloud.common.feign.annotation.EnableXHuiFeignClients;
import com.xhuicloud.common.security.annotation.EnableXHuiResourceServer;
import com.xhuicloud.common.swagger.annotation.EnableXHuiSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@EnableXHuiFeignClients
@EnableXHuiResourceServer
@EnableXHuiSwagger
public class XHuiUserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(XHuiUserManagementApplication.class, args);
    }
}
