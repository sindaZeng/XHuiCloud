package com.zsinda.fdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FdpAuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FdpAuthServerApplication.class,args);
    }
}
