package com.zsinda.fdp;

import com.zsinda.fdp.annotation.EnableFdpResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: FDPlatform
 * @description: FdpPayApplication
 * @author: Sinda
 * @create: 2020-06-03 11:17
 */
@SpringBootApplication
@EnableFeignClients
@EnableFdpResourceServer
public class FdpPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FdpPayApplication.class, args);
    }

}
