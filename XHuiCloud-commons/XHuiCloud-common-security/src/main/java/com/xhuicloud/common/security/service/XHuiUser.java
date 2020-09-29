package com.xhuicloud.common.security.service;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @program: XHuiCloud
 * @description: XHuiUser
 * @author: Sinda
 * @create: 2019-12-26 00:52
 **/
public class XHuiUser extends User {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 用户ID
     */
    @Getter
    private Integer id;

    /**
     * 手机号
     */
    @Getter
    private String phone;

    /**
     * 租户ID
     */
    @Getter
    private Integer tenantId;

    public XHuiUser(Integer id, String phone, Integer tenantId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.phone = phone;
        this.tenantId = tenantId;
    }
}
