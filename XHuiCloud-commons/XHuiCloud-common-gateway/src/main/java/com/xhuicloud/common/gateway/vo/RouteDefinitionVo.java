package com.xhuicloud.common.gateway.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.io.Serializable;

/**
 * @program: XHuiCloud
 * @description: RouteDefinitionVo
 * @author: Sinda
 * @create: 2020-03-02 17:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RouteDefinitionVo extends RouteDefinition implements Serializable {
    /**
     * 路由名称
     */
    private String routeName;
}
