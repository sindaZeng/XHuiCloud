package com.xhuicloud.common.swagger.config;

import com.xhuicloud.common.swagger.handle.SwaggerResourceHandler;
import com.xhuicloud.common.swagger.handle.SwaggerSecurityHandler;
import com.xhuicloud.common.swagger.handle.SwaggerUiHandler;
import com.xhuicloud.common.swagger.properties.SwaggerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class SwaggerAutoConfiguration {

    @Bean
    @Primary
    public SwaggerProvider swaggerProvider(RouteDefinitionRepository routeDefinitionRepository, SwaggerProperties swaggerProperties,
                                           DiscoveryClient discoveryClient) {
        return new SwaggerProvider(routeDefinitionRepository, swaggerProperties, discoveryClient);
    }

    @Bean
    public SwaggerResourceHandler swaggerResourceHandler(SwaggerProvider swaggerProvider) {
        return new SwaggerResourceHandler(swaggerProvider);
    }

    @Bean
    public FluxSwaggerConfiguration fluxSwaggerConfiguration() {
        return new FluxSwaggerConfiguration();
    }

    @Bean
    public SwaggerSecurityHandler swaggerSecurityHandler(
            ObjectProvider<SecurityConfiguration> securityConfigurationObjectProvider) {
        SecurityConfiguration securityConfiguration = securityConfigurationObjectProvider
                .getIfAvailable(() -> SecurityConfigurationBuilder.builder().build());
        return new SwaggerSecurityHandler(securityConfiguration);
    }

    @Bean
    public SwaggerUiHandler swaggerUiHandler(ObjectProvider<UiConfiguration> uiConfigurationObjectProvider) {
        UiConfiguration uiConfiguration = uiConfigurationObjectProvider
                .getIfAvailable(() -> UiConfigurationBuilder.builder().build());
        return new SwaggerUiHandler(uiConfiguration);
    }

    @Bean
    public RouterFunction swaggerRouterFunction(SwaggerProperties swaggerProperties, SwaggerUiHandler swaggerUiHandler,
                                                SwaggerSecurityHandler swaggerSecurityHandler, SwaggerResourceHandler swaggerResourceHandler) {
        // 开启swagger 正常执行路由
        if (swaggerProperties.getEnabled()) {
            return RouterFunctions.route(RequestPredicates.GET("/swagger-resources")
                    .and(RequestPredicates.accept(MediaType.ALL)), swaggerResourceHandler)
                    .andRoute(RequestPredicates.GET("/swagger-resources/configuration/ui")
                            .and(RequestPredicates.accept(MediaType.ALL)), swaggerUiHandler)
                    .andRoute(RequestPredicates.GET("/swagger-resources/configuration/security")
                            .and(RequestPredicates.accept(MediaType.ALL)), swaggerSecurityHandler);
        } else {
            // 关闭时，返回404
            return RouterFunctions
                    .route(RequestPredicates.GET("/swagger-ui/**").and(RequestPredicates.accept(MediaType.ALL)),
                            serverRequest -> ServerResponse.notFound().build())
                    .andRoute(RequestPredicates.GET("/doc.html").and(RequestPredicates.accept(MediaType.ALL)),
                            serverRequest -> ServerResponse.notFound().build());
        }
    }

}
