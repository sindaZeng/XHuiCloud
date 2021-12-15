package com.xhuicloud.common.security.social;

import com.xhuicloud.common.security.component.XHuiUserDetailsChecker;
import com.xhuicloud.common.security.service.XHuiUserDetailsService;
import com.xhuicloud.common.security.utils.SecurityMessageUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2019-12-26 22:16
 **/
@Slf4j
public class SocialAuthenticationProvider implements AuthenticationProvider {

    private MessageSourceAccessor messages = SecurityMessageUtil.getAccessor();

    private UserDetailsChecker detailsChecker = new XHuiUserDetailsChecker();

    @Getter
    @Setter
    private XHuiUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialAuthenticationToken authenticationToken = (SocialAuthenticationToken) authentication;
        String principal = authenticationToken.getPrincipal().toString();
        UserDetails userDetails = userDetailsService.loadUserBySocial(principal);
        if (userDetails == null) {
            log.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.noopBindAccount", "Noop Bind Account"));
        }
        detailsChecker.check(userDetails);
        SocialAuthenticationToken socialAuthenticationToken = new SocialAuthenticationToken(userDetails, userDetails.getAuthorities());
        //在之前有把请求信息放到details  但是因为是没有认证的，所有要copy到已认证的details
        socialAuthenticationToken.setDetails(authenticationToken.getDetails());
        return socialAuthenticationToken;
    }

    /**
     * AuthenticationProvider中的supports来表明支持什么样的MobileCodeAuthenticationToken
     *
     * @param authenticationToken
     * @return
     */
    @Override
    public boolean supports(Class<?> authenticationToken) {
        return SocialAuthenticationToken.class.isAssignableFrom(authenticationToken);
    }
}
