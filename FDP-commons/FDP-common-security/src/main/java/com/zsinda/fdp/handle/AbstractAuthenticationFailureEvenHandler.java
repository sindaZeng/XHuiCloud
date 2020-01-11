package com.zsinda.fdp.handle;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: FDPlatform
 * @description: AbstractAuthenticationFailureEvenHandler
 * @author: Sinda
 * @create: 2020-01-11 17:14
 */
public abstract class AbstractAuthenticationFailureEvenHandler implements ApplicationListener<AbstractAuthenticationFailureEvent> {



    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();

        AuthenticationException authenticationException = event.getException();
        Authentication authentication = (Authentication) event.getSource();

        handle(authenticationException, authentication, request, response);
    }

    public abstract void handle(AuthenticationException authenticationException, Authentication authentication
            , HttpServletRequest request, HttpServletResponse response);
}
