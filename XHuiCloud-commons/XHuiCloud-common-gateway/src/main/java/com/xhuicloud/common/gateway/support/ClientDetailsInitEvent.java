package com.xhuicloud.common.gateway.support;

import org.springframework.context.ApplicationEvent;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/10/27
 */
public class ClientDetailsInitEvent extends ApplicationEvent {

    public ClientDetailsInitEvent(Object source) {
        super(source);
    }

}
