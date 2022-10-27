package com.xhuicloud.gateway.route;

import cn.hutool.extra.spring.SpringUtil;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhuicloud.common.core.utils.Response;
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
 * @Desc
 * @Author Sinda
 * @Date 2022/10/23
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImageCodeCheckHandler implements HandlerFunction<ServerResponse> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public Mono<ServerResponse> handle(ServerRequest request) {
        CaptchaVO vo = new CaptchaVO();
        vo.setPointJson(request.queryParam("pointJson").get());
        vo.setToken(request.queryParam("token").get());
        vo.setCaptchaType("blockPuzzle");

        CaptchaService captchaService = SpringUtil.getBean(CaptchaService.class);
        ResponseModel responseModel = captchaService.check(vo);

        return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(objectMapper.writeValueAsString(Response.success(responseModel))));
    }
}