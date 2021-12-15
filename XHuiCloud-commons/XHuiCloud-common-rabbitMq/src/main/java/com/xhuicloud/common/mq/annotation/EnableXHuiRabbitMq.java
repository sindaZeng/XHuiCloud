package com.xhuicloud.common.mq.annotation;

import com.xhuicloud.common.mq.config.XHuiRabbitMqBaseConfig;
import com.xhuicloud.common.mq.properties.XHuiRabbitMqProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(XHuiRabbitMqProperties.class)
@Import(XHuiRabbitMqBaseConfig.class)
public @interface EnableXHuiRabbitMq {

}
