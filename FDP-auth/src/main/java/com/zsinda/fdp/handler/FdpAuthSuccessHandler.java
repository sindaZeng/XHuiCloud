package com.zsinda.fdp.handler;

import com.zsinda.fdp.handle.AbstractAuthenticationSuccessEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: FDPlatform
 * @description: 登录成功处理器
 * @author: Sinda
 * @create: 2020-01-11 16:47
 */
@Slf4j
@Component
public class FdpAuthSuccessHandler extends AbstractAuthenticationSuccessEventHandler {

    @Override
    public void handle(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        log.info("用户：{} 登录成功", authentication.getPrincipal());
    }
}
