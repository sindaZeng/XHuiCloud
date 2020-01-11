package com.zsinda.fdp.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @program: FDPlatform
 * @description: FdpUser
 * @author: Sinda
 * @create: 2019-12-26 00:52
 **/
public class FdpUser extends User {

    /**
     * 用户ID
     */
    @Getter
    @Setter
    private Integer id;

    public FdpUser(Integer id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }
}
