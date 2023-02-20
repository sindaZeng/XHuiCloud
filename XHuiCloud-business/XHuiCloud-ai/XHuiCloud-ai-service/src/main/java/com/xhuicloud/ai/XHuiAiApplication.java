package com.xhuicloud.ai;

import com.xhuicloud.common.authorization.resource.annotation.EnableResourceServer;
import com.xhuicloud.common.feign.annotation.EnableXHuiFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
@SpringBootApplication
@EnableXHuiFeignClients
@EnableResourceServer
public class XHuiAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(XHuiAiApplication.class, args);
    }

}
