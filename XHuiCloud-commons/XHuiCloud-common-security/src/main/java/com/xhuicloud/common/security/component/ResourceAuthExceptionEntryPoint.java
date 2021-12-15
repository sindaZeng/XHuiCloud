package com.xhuicloud.common.security.component;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.security.utils.SecurityMessageUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @program: XHuiCloud
 * @description: 客户端异常处理
 * @author: Sinda
 * @create: 2020-03-04 14:34:43
 */
@Slf4j
@Component
@AllArgsConstructor
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        response.setCharacterEncoding(CharEncoding.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Response<String> result = new Response<>();
        result.setMsg(authException.getMessage());
        result.setData(authException.getMessage());
        result.setCode(CommonConstants.REQUEST_FAIL);

        if (authException instanceof CredentialsExpiredException
                || authException instanceof InsufficientAuthenticationException) {
            String msg = SecurityMessageUtil.getAccessor().getMessage(
                    "AbstractUserDetailsAuthenticationProvider.credentialsExpired", authException.getMessage());
            result.setMsg(msg);
        }
        if (authException instanceof UsernameNotFoundException) {
            String msg = SecurityMessageUtil.getAccessor().getMessage(
                    "AbstractUserDetailsAuthenticationProvider.noopBindAccount", authException.getMessage());
            result.setMsg(msg);
        }
        if (authException instanceof BadCredentialsException) {
            String msg = SecurityMessageUtil.getAccessor().getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badClientCredentials", authException.getMessage());
            result.setMsg(msg);
        }
        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
