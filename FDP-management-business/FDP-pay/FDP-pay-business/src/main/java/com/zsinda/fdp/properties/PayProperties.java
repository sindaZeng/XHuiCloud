package com.zsinda.fdp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @program: FDPlatform
 * @description: PayProperties
 * @author: Sinda
 * @create: 2020-06-15 09:37
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "pay")
public class PayProperties {

    private String domain;

    private Alipay alipay;

    private WeChat weChat;

    @Data
    public class Alipay{

        /**
         * 同步通知
         */
        private String returnUrl;

        /**
         * 异步通知
         */
        private String notifyUrl;

        /**
         * 订单过期时间
         */
        private String expireTime;

    }

    @Data
    public class WeChat{

        /**
         * 同步通知
         */
        private String refundNotifyUrl;

        /**
         * 异步通知
         */
        private String notifyUrl;

        /**
         * 订单过期时间
         */
        private String expireTime;

    }
}
