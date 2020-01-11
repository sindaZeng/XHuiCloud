package com.alibaba.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author nacos
 */
@SpringBootApplication
@EnableScheduling
public class FpdNacosApplication {

    public static void main(String[] args) {
        //默认集群模式启动，改为单机
        System.setProperty("nacos.standalone", "true");
        SpringApplication.run(FpdNacosApplication.class, args);
    }
}
