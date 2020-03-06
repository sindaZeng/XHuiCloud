package com.zsinda.fdp;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: FDPlatform
 * @description: FdpTaskApplication
 * @author: Sinda
 * @create: 2020-03-05 23:15
 */

@SpringCloudApplication
@EnableFeignClients
public class FdpTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(FdpTaskApplication.class, args);
    }
}
