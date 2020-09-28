package com.xhuicloud.pay;

import com.xhuicloud.common.feign.annotation.EnableXHuiFeignClients;
import com.xhuicloud.common.security.annotation.EnableXHuiResourceServer;
import com.xhuicloud.common.swagger.annotation.EnableXHuiSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: XHuiCloud
 * @description: XHuiPayApplication
 * @author: Sinda
 * @create: 2020-06-03 11:17
 */
@SpringCloudApplication
@EnableXHuiFeignClients
@EnableXHuiResourceServer
@EnableXHuiSwagger
public class XHuiPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(XHuiPayApplication.class, args);
    }

}
