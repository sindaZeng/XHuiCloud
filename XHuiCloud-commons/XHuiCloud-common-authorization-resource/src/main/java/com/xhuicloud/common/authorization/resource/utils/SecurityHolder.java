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

package com.xhuicloud.common.authorization.resource.utils;

import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.authorization.resource.userdetails.XHuiUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.ROLE_PREFIX;

/**
 * @program: XHuiCloud
 * @description: SpringSecurity工具类
 * @author: Sinda
 * @create: 2020-01-01 18:21
 */
@UtilityClass
public class SecurityHolder {

    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public String encoder(String password) {
        return passwordEncoder().encode(password);
    }

    /**
     * 获取Authentication
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     */
    public XHuiUser getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof XHuiUser) {
            return (XHuiUser) principal;
        }
        return null;
    }

    /**
     * 获取用户
     */
    public XHuiUser getUser() {
        Authentication authentication = getAuthentication();
        return getUser(authentication);
    }

    public String getOperator() {
        XHuiUser user = getUser();
        return user.getName() + "(" + user.getId() + ")";
    }

    /**
     * 获取用户Id
     */
    public Integer getUserId() {
        return getUser().getId();
    }

    /**
     * 获取用户角色信息
     *
     * @return 角色集合
     */
    public List<String> getRoles() {
        Authentication authentication = getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roleCodes = new ArrayList<>();
        authorities.stream()
                .filter(granted -> StrUtil.startWith(granted.getAuthority(), ROLE_PREFIX))
                .forEach(granted -> {
                    String code = StrUtil.removePrefix(granted.getAuthority(), ROLE_PREFIX);
                    roleCodes.add(code);
                });
        return roleCodes;
    }

}
