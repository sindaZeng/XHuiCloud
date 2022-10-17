package com.xhuicloud.gateway.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.gateway.utils.VerifyCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @Desc 验证码校验,后续处理交给客户端
 * @Author Sinda
 * @Date 2022/9/26
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MobileCodeCheckHandler implements HandlerFunction<ServerResponse> {

    private final ObjectMapper objectMapper;

    private final VerifyCodeUtil verifyCodeUtil;

    @Override
    @SneakyThrows
    public Mono<ServerResponse> handle(ServerRequest request) {
        ServerHttpRequest serverHttpRequest = request.exchange().getRequest();
        verifyCodeUtil.validateCode(serverHttpRequest);
        return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(objectMapper.writeValueAsString(Response.success(true))));
    }
}
