package com.xhuicloud.common.gateway.support;

import org.springframework.context.ApplicationEvent;

/**
 * @program: XHuiCloud
 * @description: DynamicRouteInitEvent
 * @author: Sinda
 * @create: 2020-03-02 17:35
 */
public class DynamicRouteInitEvent extends ApplicationEvent {
    public DynamicRouteInitEvent(Object source) {
        super(source);
    }
}
