package com.xhuicloud.common.core.thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "xhui.thread.pool")
public class AsyncThreadExecuteProperties {

    private int corePoolSize = 20;

    private int maxPoolSize = 40;

    private int keepAliveSeconds = 300;

    private int queueCapacity = 50;

}
