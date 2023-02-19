package com.xhuicloud.common.mqtt.server.auth;

import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.core.server.auth.IMqttServerAuthHandler;
import org.tio.core.ChannelContext;

/**
 * @Desc mqtt tcp、websocket 认证，请按照自己的需求和业务进行扩展
 * @Author Sinda
 * @Date 2023/2/14
 */
@Slf4j
//@ConditionalOnMissingBean
//@Configuration(proxyBeanMethods = false)
public class MqttAuthHandler implements IMqttServerAuthHandler {

    @Override
    public boolean authenticate(ChannelContext context, String uniqueId, String clientId, String userName, String password) {
        return true;
    }

}
