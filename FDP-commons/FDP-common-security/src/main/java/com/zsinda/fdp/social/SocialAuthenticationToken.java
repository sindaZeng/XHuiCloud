package com.zsinda.fdp.social;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @program: FDPlatform
 * @description: MobileCodeAuthenticationToken
 * @author: Sinda
 * @create: 2019-12-26 21:59
 **/
public class SocialAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 510L;

    /**
     * 认证之前放手机号。或者code 认证之后放用户信息
     */
    private final Object principal;


    public SocialAuthenticationToken(String mobile) {
        super(null);
        this.principal = mobile;
        this.setAuthenticated(false);
    }

    /**
     * 登陆成功后
     *
     * @param principal
     * @param authorities
     */
    public SocialAuthenticationToken(Object principal,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
