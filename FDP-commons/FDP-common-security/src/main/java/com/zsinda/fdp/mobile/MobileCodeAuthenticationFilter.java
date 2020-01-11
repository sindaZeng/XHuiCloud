package com.zsinda.fdp.mobile;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: FDPlatform
 * @description: MobileCodeAuthenticationFilter
 * @author: Sinda
 * @create: 2019-12-26 22:05
 **/
public class MobileCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";
    //请求中 携带手机号的参数名字
    private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;
    private boolean postOnly = true;

    //当前过滤器要处理的请求
    public MobileCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/token/mobile", "POST"));
    }

    /**
     * 认证
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //判断当前请求是否为post
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            //获取手机号
            String mobile = obtainMobile(request);
            if (mobile == null) {
                mobile = "";
            }
            mobile = mobile.trim();
            //实例化token 实例化自己的
            MobileCodeAuthenticationToken authRequest = new MobileCodeAuthenticationToken(mobile);
            //把请求信息设置到token
            setDetails(request, authRequest);
            //由AuthenticationManager 挑选一个provider 来处理MobileCodeAuthenticationToken校验逻辑
            // AuthenticationProvider中的supports来表明支持什么样的MobileCodeAuthenticationToken
            return getAuthenticationManager().authenticate(authRequest);
        }
    }

    /**
     * 获取手机号
     * @param request
     * @return
     */
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    /**
     * 设置请求参数
     * @param request
     * @param authRequest
     */
    protected void setDetails(HttpServletRequest request, MobileCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }



    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }


    public final String getMobileParameter() {
        return mobileParameter;
    }
}
