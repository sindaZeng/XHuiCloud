package com.xhuicloud.gateway.config;

import com.google.common.collect.Lists;
import com.xhuicloud.common.core.utils.BeanUtils;
import com.xhuicloud.common.gateway.vo.RouteDefinitionVo;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: XHuiCloud
 * @description: SwaggerProvider
 * @author: Sinda
 * @create: 2020-02-21 14:06
 */
@Primary
@Component
@AllArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {
    private static final String API_URI = "/v2/api-docs";
    private final RouteDefinitionRepository routeDefinitionRepository;
    private final FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    @Override
    public List<SwaggerResource> get() {
        List<Object> routes = Lists.newArrayList();
        List<RouteDefinitionVo> routeDefinitionVos = Lists.newArrayList();
        routeDefinitionRepository.getRouteDefinitions().subscribe(routes::add);
        BeanUtils.copyBeanList(routes,routeDefinitionVos,RouteDefinitionVo.class);
        List<SwaggerResource> collect = routeDefinitionVos.stream().flatMap(routeDefinition -> routeDefinition.getPredicates().stream()
                .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                .filter(predicateDefinition -> !filterIgnorePropertiesConfig.getSwaggerProviders().contains(routeDefinition.getId()))
                .map(predicateDefinition ->
                        swaggerResource(routeDefinition.getRouteName(), predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", API_URI))
                )).sorted(Comparator.comparing(SwaggerResource::getName))
                .collect(Collectors.toList());
        return collect;
    }

    private static SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
