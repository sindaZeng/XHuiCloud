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

package com.xhuicloud.common.authorization.resource.userdetails;

import cn.hutool.core.util.ArrayUtil;
import com.xhuicloud.common.authorization.resource.constant.CustomAuthorizationGrantType;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.core.ttl.XHuiCommonThreadLocalHolder;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysUser;
import com.xhuicloud.upms.feign.SysSocialServiceFeign;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

import java.util.*;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.*;

/**
 * InitializeUserDetailsManagerConfigurer初始化时，初始化了DaoAuthenticationProvider
 * 导致: ProviderManager parent属性有值，最后都会执行DaoAuthenticationProvider,错误会被覆盖成:密码错误
 * 目前解决方案是多注入一个XHuiUserDetailsService bean
 */
@Primary
public class XHuiUserDetailsServiceImpl implements XHuiUserDetailsService {

    private final SysSocialServiceFeign socialServiceFeign;

    private final CacheManager cacheManager;

    public XHuiUserDetailsServiceImpl(SysSocialServiceFeign socialServiceFeign, CacheManager cacheManager) {
        this.socialServiceFeign = socialServiceFeign;
        this.cacheManager = cacheManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserDetailsByUserName(username);
    }

    @Override
    public UserDetails loadUserBySocial(String username, String grantType) throws UsernameNotFoundException {
        Response<UserInfo> sysUser = socialServiceFeign.getSysUser(grantType, username, IS_COMMING_ANONYMOUS_YES);
        if (!sysUser.isSuccess()) {
            OAuth2Error oAuth2Error = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST);
            throw new OAuth2AuthenticationException(oAuth2Error, sysUser.getMsg());
        }
        return getUserDetailsByUserInfo(sysUser.getData());
    }

    @Override
    public UserDetails getUserDetails(Map<String, Object> claims) {
        String username = (String) claims.get(IdTokenClaimNames.SUB);
        Object userId = claims.get(CommonConstants.USER_ID);
        Object tenantId = claims.get(CommonConstants.TENANT_ID);
        if (tenantId instanceof Long) {
            XHuiCommonThreadLocalHolder.setTenant((Long) tenantId);
        } else {
            XHuiCommonThreadLocalHolder.setTenant(Long.valueOf(tenantId.toString()));
        }
        if (userId instanceof Long) {
            XHuiCommonThreadLocalHolder.setUser((Long) userId);
        } else {
            XHuiCommonThreadLocalHolder.setUser(Long.valueOf(userId.toString()));
        }
        return getUserDetailsByUserName(username);
    }

    public UserDetails getUserDetailsByUserName(String username) {
        Cache cache = cacheManager.getCache(CacheConstants.SYS_USER);
        if (cache != null && cache.get(username) != null) {
            return cache.get(username, UserDetails.class);
        }
        UserDetails userDetails = getUserDetailsByUserInfo(socialServiceFeign.getSysUser(CustomAuthorizationGrantType.PASSWORD.getValue(), username, IS_COMMING_ANONYMOUS_YES).getData());
        cache.put(username, userDetails);
        return userDetails;
    }

    public UserDetails getUserDetailsByUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        Set<String> dbAuthsSet = new HashSet<>();
        if (ArrayUtil.isNotEmpty(userInfo.getRoles())) {
            // 获取角色
            Arrays.stream(userInfo.getRoles()).forEach(role -> dbAuthsSet.add(ROLE_PREFIX + role));
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(userInfo.getPermissions()));
        }
        Collection<? extends GrantedAuthority> authorities
                = AuthorityUtils.createAuthorityList(ArrayUtil.toArray(dbAuthsSet, String.class));
        SysUser user = userInfo.getSysUser();
        // 构造security用户
        return new XHuiUser(user.getUserId(), user.getPhone(), user.getTenantId(), userInfo.getTenantName(), user.getUsername(), user.getPassword(), true,
                true, true, user.getLockFlag() != USER_IS_LOCK, authorities);
    }

}
