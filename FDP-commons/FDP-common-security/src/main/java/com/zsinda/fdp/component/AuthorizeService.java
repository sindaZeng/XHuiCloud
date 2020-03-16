package com.zsinda.fdp.component;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @program: FDPlatform
 * @description: AuthorizeService
 * @author: Sinda
 * @create: 2020-03-16 00:33
 */
@Slf4j
@Component("authorize")
public class AuthorizeService {
    /**
     * 判断接口是否有权限
     *
     * @param permissions 权限
     * @return {boolean}
     */
    public boolean hasPermission(String... permissions) {
        if (ArrayUtil.isEmpty(permissions)) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(StringUtils::hasText)
                .anyMatch(x -> PatternMatchUtils.simpleMatch(permissions, x));
    }
}
