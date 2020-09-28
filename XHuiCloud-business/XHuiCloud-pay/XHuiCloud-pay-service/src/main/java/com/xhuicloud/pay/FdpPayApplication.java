package com.xhuicloud.pay;

import com.xhuicloud.common.security.annotation.EnableXHuiResourceServer;
import com.xhuicloud.common.swagger.annotation.EnableXHuiSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: XHuiCloud
 * @description: FdpPayApplication
 * @author: Sinda
 * @create: 2020-06-03 11:17
 */
@SpringBootApplication
@EnableFeignClients
@EnableXHuiResourceServer
@EnableXHuiSwagger
public class FdpPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FdpPayApplication.class, args);
    }

}
