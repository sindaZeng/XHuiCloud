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

package com.xhuicloud.common.authorization.extension;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

public class CustomExceptionTranslator {

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public OAuth2AuthenticationException translate(Authentication authentication,
                                                   AuthenticationException authenticationException) {

        if (authenticationException instanceof UsernameNotFoundException) {
            return new OAuth2AuthenticationException(new OAuth2Error("username_not_found",
                    this.messages.getMessage("JdbcDaoImpl.notFound", new Object[]{authentication.getName()},
                            "Username {0} not found"), ""));
        } else if (authenticationException instanceof LockedException) {
            return new OAuth2AuthenticationException(new OAuth2Error("user_locked", this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"), ""));
        } else if (authenticationException instanceof DisabledException) {
            return new OAuth2AuthenticationException(new OAuth2Error("user_disable",
                    this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"),
                    ""));
        } else if (authenticationException instanceof BadCredentialsException) {
            return new OAuth2AuthenticationException(
                    new OAuth2Error("bad_credentials", this.messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), ""));
        } else if (authenticationException instanceof AccountExpiredException) {
            return new OAuth2AuthenticationException(new OAuth2Error("user_expired", this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"), ""));
        } else if (authenticationException instanceof CredentialsExpiredException) {
            return new OAuth2AuthenticationException(new OAuth2Error("credentials_expired",
                    this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired",
                            "User credentials have expired"),
                    ""));
        } else if (authenticationException instanceof OAuth2AuthenticationException) {
            return (OAuth2AuthenticationException) authenticationException;
        }
        return new OAuth2AuthenticationException("unknown_error");
    }
}
