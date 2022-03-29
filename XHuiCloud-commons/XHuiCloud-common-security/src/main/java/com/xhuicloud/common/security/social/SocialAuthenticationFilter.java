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

package com.xhuicloud.common.security.social;

import com.xhuicloud.common.core.constant.SecurityConstants;
import com.xhuicloud.common.core.enums.login.LoginTypeEnum;
import com.xhuicloud.common.core.exception.SysException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Locale;

/**
 * @program: XHuiCloud
 * @description: MobileCodeAuthenticationFilter
 * @author: Sinda
 * @create: 2019-12-26 22:05
 **/
@Slf4j
public class SocialAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 请求中 携带手机号的参数名字
     */
    public static final String authCodeParameter = "auth_code";

    @Getter
    private boolean postOnly = true;

    @Getter
    @Setter
    private AuthenticationEventPublisher eventPublisher;

    @Getter
    @Setter
    private AuthenticationEntryPoint authenticationEntryPoint;

    //当前过滤器要处理的请求
    public SocialAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.TOKEN_SOCIAL, HttpMethod.POST.name()));
    }

    /**
     * 认证
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String requestURI = request.getRequestURI();

        //判断当前请求是否为post
        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String[] urlArray = requestURI.split("/");
            String loginType = urlArray[urlArray.length-1];
            if (loginType == null || !Arrays.stream(LoginTypeEnum.values()).anyMatch(type -> type.equals(loginType))) {
                throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            }
            //获取授权凭证
            String auth_code = getAuthCodeParameter(request);
            if (auth_code == null) {
                auth_code = "";
            }
            auth_code = auth_code.trim();
            //实例化token 实例化自己的
            SocialAuthenticationToken authRequest = new SocialAuthenticationToken(auth_code);
            //把请求信息设置到token
            setDetails(request, authRequest);
            //由AuthenticationManager 挑选一个provider 来处理MobileCodeAuthenticationToken校验逻辑
            // AuthenticationProvider中的supports来表明支持什么样的MobileCodeAuthenticationToken
            Authentication authenticate = null;
            try {
                authenticate = getAuthenticationManager().authenticate(authRequest);
                SecurityContextHolder.getContext().setAuthentication(authenticate);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();

                if (log.isDebugEnabled()) {
                    logger.debug("Authentication request failed: " + e);
                }

                eventPublisher.publishAuthenticationFailure(new BadCredentialsException(e.getMessage(), e),
                        new PreAuthenticatedAuthenticationToken("access-token", "N/A"));

                try {
                    authenticationEntryPoint.commence(request, response,
                            new UsernameNotFoundException(e.getMessage(), e));
                } catch (Exception ex) {
                    logger.error("authenticationEntryPoint handle error:{}", ex);
                }
            }
            return authenticate;
        }
    }

    /**
     * 获取授权凭证
     *
     * @param request
     * @return
     */
    protected String getAuthCodeParameter(HttpServletRequest request) {
        return request.getParameter(authCodeParameter);
    }

    /**
     * 设置请求参数
     *
     * @param request
     * @param authRequest
     */
    protected void setDetails(HttpServletRequest request, SocialAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}
