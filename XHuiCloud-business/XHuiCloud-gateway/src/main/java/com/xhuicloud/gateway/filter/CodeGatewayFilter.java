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

package com.xhuicloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhuicloud.common.core.constant.SecurityConstants;
import com.xhuicloud.common.core.enums.login.LoginTypeEnum;
import com.xhuicloud.common.core.exception.ValidateCodeException;
import com.xhuicloud.common.core.utils.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

/**
 * @program: XHuiCloud
 * @description: 验证码校验
 * @author: Sinda
 * @create: 2020-06-17 09:24
 */
@Slf4j
@Component
@AllArgsConstructor
public class CodeGatewayFilter extends AbstractGatewayFilterFactory {

    private final ObjectMapper objectMapper;

    private final RedisTemplate redisTemplate;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            AntPathMatcher matcher = new AntPathMatcher();
            // 不是登录请求，直接向下执行
            if (!matcher.match(SecurityConstants.OAUTH_TOKEN, request.getURI().getPath())
                    && !matcher.match(SecurityConstants.TOKEN_SOCIAL, request.getURI().getPath())) {
                return chain.filter(exchange);
            }

            // 刷新token，直接向下执行
            String grantType = request.getQueryParams().getFirst("grant_type");
            if (StrUtil.equals(SecurityConstants.REFRESH_TOKEN, grantType)) {
                return chain.filter(exchange);
            }

            try {
                // 如果是第三方社交登录 判断授权码的合法性
                if (matcher.match(SecurityConstants.TOKEN_SOCIAL, request.getURI().getPath())) {
                    String auth_code = request.getQueryParams().getFirst("auth_code");
                    if (StrUtil.containsAny(auth_code, LoginTypeEnum.SMS.getType())) {
                        //验证码登录 校验验证码
                        validateCode(request);
                    }
                    return chain.filter(exchange);
                }


            } catch (Exception e) {
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
                try {
                    return response.writeWith(Mono.just(response.bufferFactory()
                            .wrap(objectMapper.writeValueAsBytes(
                                    Response.failed(e.getMessage())))));
                } catch (JsonProcessingException e1) {
                    log.error("对象输出异常", e1);
                }
            }
            return chain.filter(exchange);
        };
    }

    private void validateCode(ServerHttpRequest request) {
        String code = request.getQueryParams().getFirst("code");

        if (StringUtils.isBlank(code)) {
            throw ValidateCodeException.validateFail(ValidateCodeException.CODE_IS_NULL_FAIL);
        }

        String mobile = request.getQueryParams().getFirst("auth_code");
        if (StrUtil.isBlank(mobile)) {
            throw ValidateCodeException.validateFail(ValidateCodeException.MOBILE_IS_NULL_FAIL);
        }
        String key = SecurityConstants.CODE_KEY + mobile.split("@")[1];
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        String codeStr = (String) redisTemplate.opsForValue().get(key);

        if (StringUtils.isBlank(codeStr)) {
            throw ValidateCodeException.validateFail(ValidateCodeException.CODE_OVERDUE_FAIL);
        }

        if (!StringUtils.equals(codeStr, code)) {
            throw ValidateCodeException.validateFail(ValidateCodeException.CODE_VALIDATE_FAIL);
        }
        redisTemplate.delete(key);
    }
}
