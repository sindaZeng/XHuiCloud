package com.zsinda.fdp.support;

import org.springframework.context.ApplicationEvent;

/**
 * @program: FDPlatform
 * @description: DynamicRouteInitEvent
 * @author: Sinda
 * @create: 2020-03-02 17:35
 */
public class DynamicRouteInitEvent extends ApplicationEvent {
    public DynamicRouteInitEvent(Object source) {
        super(source);
    }
}
