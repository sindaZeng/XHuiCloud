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
import com.xhuicloud.common.data.ttl.XHuiCommonThreadLocalHolder;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysUser;
import com.xhuicloud.upms.feign.SysSocialServiceFeign;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

import java.util.*;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.*;

@AllArgsConstructor
public class XHuiUserDetailsServiceImpl implements XHuiUserDetailsService {

    private final SysSocialServiceFeign socialServiceFeign;

    private final CacheManager cacheManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserDetails(username);
    }

    @Override
    public UserDetails loadUserBySocial(String username, String grantType) throws UsernameNotFoundException {
        Cache cache = cacheManager.getCache(CacheConstants.SYS_USER);
        if (cache != null && cache.get(username) != null) {
            return cache.get(username, UserDetails.class);
        }
        UserDetails userDetails = getUserDetails(socialServiceFeign.getSysUser(grantType, username, IS_COMMING_ANONYMOUS_YES).getData());
        cache.put(username, userDetails);
        return userDetails;
    }

    @Override
    public UserDetails getUserDetails(Map<String, Object> claims) {
        String username = (String) claims.get(IdTokenClaimNames.SUB);
        Object tenantId = claims.get(CommonConstants.TENANT_ID);
        // JSON序列化 Int Long 问题
        if (tenantId instanceof Long) {
            XHuiCommonThreadLocalHolder.setTenant(((Long) tenantId).intValue());
        } else {
            XHuiCommonThreadLocalHolder.setTenant((Integer) tenantId);
        }
        return getUserDetails(username);
    }

    public UserDetails getUserDetails(String username) {
        Cache cache = cacheManager.getCache(CacheConstants.SYS_USER);
        if (cache != null && cache.get(username) != null) {
            return cache.get(username, UserDetails.class);
        }
        UserDetails userDetails = getUserDetails(socialServiceFeign.getSysUser(CustomAuthorizationGrantType.PASSWORD.getValue(), username, IS_COMMING_ANONYMOUS_YES).getData());
        cache.put(username, userDetails);
        return userDetails;
    }

    public UserDetails getUserDetails(UserInfo userInfo) {
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
