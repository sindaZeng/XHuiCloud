package com.zsinda.fdp;

import com.zsinda.fdp.annotation.EnableFdpResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
@EnableFdpResourceServer
public class FdpUserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(FdpUserManagementApplication.class,args);
    }
}
