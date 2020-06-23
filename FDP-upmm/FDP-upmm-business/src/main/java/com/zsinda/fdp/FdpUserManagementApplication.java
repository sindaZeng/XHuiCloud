package com.zsinda.fdp;

import com.zsinda.fdp.annotation.EnableFdpResourceServer;
import com.zsinda.fdp.annotation.EnableFdpSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableFdpResourceServer
@EnableFdpSwagger
public class FdpUserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(FdpUserManagementApplication.class, args);
    }
}
