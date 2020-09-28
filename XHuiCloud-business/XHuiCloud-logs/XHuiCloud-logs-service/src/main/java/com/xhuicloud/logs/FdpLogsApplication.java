package com.xhuicloud.logs;

import com.xhuicloud.common.security.annotation.EnableXHuiResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableXHuiResourceServer
//@EnableDynamicDataSource
public class FdpLogsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FdpLogsApplication.class, args);
    }
}
