package com.xhuicloud.job;

import com.xhuicloud.common.feign.annotation.EnableXHuiFeignClients;
import com.xhuicloud.common.xxl.annotation.EnableXxlJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: XHuiCloud
 * @description: XHuiTaskApplication
 * @author: Sinda
 * @create: 2020-03-05 23:15
 */
@SpringBootApplication
@EnableXxlJob
@EnableXHuiFeignClients
@EnableDiscoveryClient
public class XHuiJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(XHuiJobApplication.class, args);
    }
}
