package com.zsinda.fdp.mobile;

import com.zsinda.fdp.service.FdpUserDetailsService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @program: FDPlatform
 * @description: 提供校验逻辑
 * @author: Sinda
 * @create: 2019-12-26 22:16
 **/
@Slf4j
public class MobileCodeAuthenticationProvider implements AuthenticationProvider{

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Getter
    @Setter
    private FdpUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileCodeAuthenticationToken authenticationToken = (MobileCodeAuthenticationToken) authentication;
        String mobile = authenticationToken.getPrincipal().toString();
        UserDetails userDetails = userDetailsService.loadUserBySocial(mobile);
        if (userDetails == null) {
            log.debug("该手机号没有绑定用户！{}",mobile);
            throw new InternalAuthenticationServiceException("该手机号没有绑定用户！");
        }
        MobileCodeAuthenticationToken mobileCodeAuthenticationToken = new MobileCodeAuthenticationToken(userDetails,userDetails.getAuthorities());
        //在之前有把请求信息放到details  但是因为是没有认证的，所有要copy到已认证的details
        mobileCodeAuthenticationToken.setDetails(authenticationToken.getDetails());
        return mobileCodeAuthenticationToken;
    }

    /**
     * AuthenticationProvider中的supports来表明支持什么样的MobileCodeAuthenticationToken
     * @param authenticationToken
     * @return
     */
    @Override
    public boolean supports(Class<?> authenticationToken) {
        return MobileCodeAuthenticationToken.class.isAssignableFrom(authenticationToken);
    }
}
