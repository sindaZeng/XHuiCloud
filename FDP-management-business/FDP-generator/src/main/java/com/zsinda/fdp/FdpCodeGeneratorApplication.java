package com.zsinda.fdp;

import com.zsinda.fdp.annotation.EnableDynamicDataSource;
import com.zsinda.fdp.annotation.EnableFdpResourceServer;
import com.zsinda.fdp.annotation.EnableFdpSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: FDPlatform
 * @description: FdpCodeGeneratorApplication
 * @author: Sinda
 * @create: 2020-06-22 11:24
 */
@SpringBootApplication
@EnableDynamicDataSource
@EnableFeignClients
@EnableFdpResourceServer
@EnableFdpSwagger
public class FdpCodeGeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(FdpCodeGeneratorApplication.class, args);
    }
}
