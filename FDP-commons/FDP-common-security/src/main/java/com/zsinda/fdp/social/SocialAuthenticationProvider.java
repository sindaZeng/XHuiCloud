package com.zsinda.fdp.social;

import com.zsinda.fdp.service.FdpUserDetailsService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @program: FDPlatform
 * @description: 提供校验逻辑
 * @author: Sinda
 * @create: 2019-12-26 22:16
 **/
@Slf4j
public class SocialAuthenticationProvider implements AuthenticationProvider{

    @Getter
    @Setter
    private FdpUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialAuthenticationToken authenticationToken = (SocialAuthenticationToken) authentication;
        String principal = authenticationToken.getPrincipal().toString();
        UserDetails userDetails = userDetailsService.loadUserBySocial(principal);
//        不返回null 用户，社交登录直接注册一个
//        if (userDetails == null) {
//            log.debug("您没有绑定用户！{}",mobile);
//            throw new InternalAuthenticationServiceException("该手机号没有绑定用户！");
//        }
        SocialAuthenticationToken socialAuthenticationToken = new SocialAuthenticationToken(userDetails,userDetails.getAuthorities());
        //在之前有把请求信息放到details  但是因为是没有认证的，所有要copy到已认证的details
        socialAuthenticationToken.setDetails(authenticationToken.getDetails());
        return socialAuthenticationToken;
    }

    /**
     * AuthenticationProvider中的supports来表明支持什么样的MobileCodeAuthenticationToken
     * @param authenticationToken
     * @return
     */
    @Override
    public boolean supports(Class<?> authenticationToken) {
        return SocialAuthenticationToken.class.isAssignableFrom(authenticationToken);
    }
}
