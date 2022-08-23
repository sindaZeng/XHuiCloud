package com.xhuicloud.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/8/21
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class XHuiMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(XHuiMonitorApplication.class, args);
    }

}
