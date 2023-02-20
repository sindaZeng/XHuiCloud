package com.xhuicloud.wechat;

import com.xhuicloud.common.authorization.resource.annotation.EnableResourceServer;
import com.xhuicloud.common.feign.annotation.EnableXHuiFeignClients;
import com.xhuicloud.common.log.annotation.EnableAudit;
import com.xhuicloud.common.swagger.annotation.EnableXHuiSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/11/4
 */
@EnableAudit
@EnableXHuiSwagger
@SpringBootApplication
@EnableXHuiFeignClients
@EnableResourceServer
public class XHuiWechatApplication {
    public static void main(String[] args) {
        SpringApplication.run(XHuiWechatApplication.class, args);
    }
}
