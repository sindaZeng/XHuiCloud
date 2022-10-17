package com.xhuicloud.gateway.config;

import com.xhuicloud.gateway.route.MobileCodeCheckHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/9/26
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class RouterFunctionConfiguration {

    private final MobileCodeCheckHandler mobileCodeCheckHandler;

    @Bean
    public RouterFunction routerFunction() {
        return RouterFunctions
                .route(RequestPredicates.path("/mobile/check/code").and(RequestPredicates.accept(MediaType.ALL)),
                        mobileCodeCheckHandler);

    }
}
