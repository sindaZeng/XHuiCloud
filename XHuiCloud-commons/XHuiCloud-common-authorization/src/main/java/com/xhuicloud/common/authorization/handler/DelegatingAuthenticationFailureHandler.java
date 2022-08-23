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

package com.xhuicloud.common.authorization.handler;

import com.xhuicloud.common.core.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录请求 OAuth2AuthenticationException 错误委托处理
 * <p>
 * 授权请求或令牌请求缺少必需的参数
 * 客户端标识符缺失或无效
 * 无效或不匹配的重定向 URI
 * 请求的作用域无效、未知或格式不正确
 * 资源所有者或授权服务器拒绝访问请求
 * 客户端身份验证失败
 * 提供的授权（授权代码、资源所有者凭据）无效、已过期或已吊销
 */
@Slf4j
public class DelegatingAuthenticationFailureHandler extends AbstractAuthenticationHandler<AuthenticationFailureHandler> implements AuthenticationFailureHandler {

    private final MappingJackson2HttpMessageConverter httpMessageConverter = new MappingJackson2HttpMessageConverter();

    public DelegatingAuthenticationFailureHandler(AuthenticationFailureHandler... authenticationFailureHandlers) {
        super(authenticationFailureHandlers);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        sendAuthenticationFailureResponse(response, exception);
        otherHandler(request, response, exception);
    }

    @Override
    public void otherHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        for (AuthenticationFailureHandler authenticationFailureHandler : handlers) {
            try {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
            } catch (Exception e) {
                log.error("登录失败委托器异常 =====> 异常类:{}, 异常信息:{}", authenticationFailureHandler.getClass(), e.getLocalizedMessage());
            }
        }
    }

    private void sendAuthenticationFailureResponse(HttpServletResponse response, AuthenticationException exception) throws IOException {
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        OAuth2Error error = ((OAuth2AuthenticationException) exception).getError();
        this.httpMessageConverter.write(
                Response.failed(error.getDescription(), error.getErrorCode()),
                MediaType.APPLICATION_JSON,
                httpResponse);
    }


}
