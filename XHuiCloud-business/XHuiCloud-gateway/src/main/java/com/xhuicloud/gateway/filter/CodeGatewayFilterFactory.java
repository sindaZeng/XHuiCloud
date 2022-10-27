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
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.core.constant.SecurityConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.core.utils.WebUtils;
import com.xhuicloud.common.gateway.vo.ClientDefinitionVo;
import com.xhuicloud.gateway.utils.VerifyCodeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
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
public class CodeGatewayFilterFactory extends AbstractGatewayFilterFactory {
    private final ObjectMapper objectMapper;
    private final VerifyCodeUtil verifyCodeUtil;

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
                // 如果是手机登录。一定要验证码  如果客户端 开启了验证码,且是密码登录
                if (grantType.equals("mobile")) {
                    //验证码登录 校验验证码
                    verifyCodeUtil.validateCode(request);
                } else if (grantType.equals("password") && isCaptchaEnableClient(request)) {
                    verifyCodeUtil.validateCaptchaCode(request);
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

    /**
     * 是否为开启验证码的客户端
     *
     * @param request
     * @return
     */
    private boolean isCaptchaEnableClient(ServerHttpRequest request) {
        String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String clientId = WebUtils.extractClientId(header).orElse(null);
        String tenantId = request.getHeaders().getFirst(CommonConstants.TENANT_ID);

        String key = String.format("%s:%s:%s", StrUtil.isBlank(tenantId) ? CommonConstants.DEFAULT_TENANT_ID : tenantId,
                CacheConstants.CLIENT_DETAILS_EXTENSION, clientId);

        ClientDefinitionVo val = (ClientDefinitionVo) redisTemplate.opsForValue().get(key);

        if (val == null) {
            return false;
        }
        return val.getCaptchaEnable() == 1;
    }
}
