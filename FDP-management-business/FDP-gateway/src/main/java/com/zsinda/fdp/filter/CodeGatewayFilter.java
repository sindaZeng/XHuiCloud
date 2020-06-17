package com.zsinda.fdp.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsinda.fdp.constant.SecurityConstants;
import com.zsinda.fdp.enums.login.LoginTypeEnum;
import com.zsinda.fdp.exception.ValidateCodeException;
import com.zsinda.fdp.utils.R;
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
import reactor.core.publisher.Mono;

/**
 * @program: FDPlatform
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

            // 不是登录请求，直接向下执行
            if (!StrUtil.containsAnyIgnoreCase(request.getURI().getPath()
                    , SecurityConstants.OAUTH_TOKEN, SecurityConstants.TOKEN_SOCIAL)) {
                return chain.filter(exchange);
            }

            // 刷新token，直接向下执行
            String grantType = request.getQueryParams().getFirst("grant_type");
            if (StrUtil.equals(SecurityConstants.REFRESH_TOKEN, grantType)) {
                return chain.filter(exchange);
            }

            try {
                // 如果是第三方社交登录 判断授权码的合法性
                if (StrUtil.containsAnyIgnoreCase(request.getURI().getPath(), SecurityConstants.TOKEN_SOCIAL)) {
                    String auth_code = request.getQueryParams().getFirst("auth_code");
                    if (StrUtil.containsAny(auth_code, LoginTypeEnum.SMS.getType())) {
                        //验证码登录 校验验证码
                        validateCode(request);
                    }else {

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
                                    R.failed(e.getMessage())))));
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
