package com.xhuicloud.auth.handle;

import com.xhuicloud.common.security.handle.AbstractAuthenticationFailureEvenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: XHuiCloud
 * @description: 登录失败处理器
 * @author: Sinda
 * @create: 2020-01-11 16:46
 */
@Slf4j
@Component
public class XHuiAuthFailureHandler extends AbstractAuthenticationFailureEvenHandler {

    @Override
    public void handle(AuthenticationException authenticationException, Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
    }
}
