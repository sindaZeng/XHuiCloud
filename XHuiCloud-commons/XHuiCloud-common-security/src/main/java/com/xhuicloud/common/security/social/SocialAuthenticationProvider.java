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

import cn.hutool.extra.spring.SpringUtil;
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

import java.util.Map;

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

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        XHuiUserDetailsService userDetailsService = SpringUtil
                .getBean(XHuiUserDetailsService.class);
        SocialAuthenticationToken authenticationToken = (SocialAuthenticationToken) authentication;
        String type = authenticationToken.getType();
        String principal = authenticationToken.getPrincipal().toString();
        UserDetails userDetails = userDetailsService.loadUserBySocial(type, principal);
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
