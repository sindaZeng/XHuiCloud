package com.xhuicloud.common.swagger.config;

import com.xhuicloud.common.swagger.properties.SwaggerProperties;
import lombok.AllArgsConstructor;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: XHuiCloud
 * @description: SwaggerProvider
 * @author: Sinda
 * @create: 2020-02-21 14:06
 */
@AllArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {

    private static final String API_URI = "/v2/api-docs";

    private final RouteDefinitionRepository routeDefinitionRepository;

    private final SwaggerProperties swaggerProperties;

    private final DiscoveryClient discoveryClient;

    @Override
    public List<SwaggerResource> get() {
        List<RouteDefinition> routes = new ArrayList<>();
        routeDefinitionRepository.getRouteDefinitions()
                // swagger 显示服务名称根据 路由的order 字段进行升序排列
                .sort(Comparator.comparingInt(RouteDefinition::getOrder)).subscribe(routes::add);

        return routes.stream()
                .flatMap(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                        .filter(predicateDefinition -> !swaggerProperties.getIgnoreProviders()
                                .contains(routeDefinition.getId()))
                        .map(predicateDefinition -> swaggerResource(routeDefinition.getId(),
                                predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**",
                                        API_URI))))
                .filter(swaggerResource -> discoveryClient.getServices().stream()
                        .anyMatch(s -> s.equals(swaggerResource.getName())))
                .collect(Collectors.toList());
    }

    private static SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

}
