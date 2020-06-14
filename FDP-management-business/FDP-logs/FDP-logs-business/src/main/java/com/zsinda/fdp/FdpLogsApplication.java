package com.zsinda.fdp;

import com.zsinda.fdp.annotation.EnableFdpResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableFdpResourceServer
//@EnableDynamicDataSource
public class FdpLogsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FdpLogsApplication.class,args);
    }
}
