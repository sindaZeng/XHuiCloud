/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

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
