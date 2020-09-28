package com.xhuicloud.generator;

import com.xhuicloud.common.datasource.annotation.EnableDynamicDataSource;
import com.xhuicloud.common.security.annotation.EnableXHuiResourceServer;
import com.xhuicloud.common.swagger.annotation.EnableXHuiSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: XHuiCloud
 * @description: XHuiCodeGeneratorApplication
 * @author: Sinda
 * @create: 2020-06-22 11:24
 */
@SpringBootApplication
@EnableDynamicDataSource
@EnableFeignClients
@EnableXHuiResourceServer
@EnableXHuiSwagger
public class XHuiCodeGeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(XHuiCodeGeneratorApplication.class, args);
    }
}
