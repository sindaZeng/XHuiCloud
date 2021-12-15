package com.xhuicloud.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysUser;
import com.xhuicloud.upms.feign.SysSocialServiceFeign;
import com.xhuicloud.upms.feign.SysUserServiceFeign;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.*;


/**
 * @program: XHuiCloud
 * @description: XHuiUserDetailsServiceImpl
 * @author: Sinda
 * @create: 2019-12-26 00:12
 **/
@Service
@AllArgsConstructor
public class XHuiUserDetailsServiceImpl implements XHuiUserDetailsService {

    private final SysUserServiceFeign sysUserServiceFeign;

    private final SysSocialServiceFeign sysSocialServiceFeign;

    private final CacheManager cacheManager;

    /**
     * 用户名登录
     *
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

//        Cache cache = cacheManager.getCache(CacheConstants.SYS_USER);
//        if (cache != null && cache.get(userName) != null) {
//            return cache.get(userName, XHuiUser.class);
//        }
        UserDetails userDetails = getUserDetails(sysUserServiceFeign.getSysUser(userName, IS_COMMING_INNER_YES).getData());
//        cache.put(userName, userDetails);
        return userDetails;
    }

    /**
     * 社交登录
     *
     * @param code
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserBySocial(String code) throws UsernameNotFoundException {
        return getUserDetails(sysSocialServiceFeign.getSysUser(code, IS_COMMING_INNER_YES).getData());
    }

    private UserDetails getUserDetails(UserInfo userInfo) {
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        Set<String> dbAuthsSet = new HashSet<>();
        if (ArrayUtil.isNotEmpty(userInfo.getRoles())) {
            // 获取角色
            Arrays.stream(userInfo.getRoles()).forEach(roleId -> dbAuthsSet.add(ROLE_PREFIX + roleId));
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(userInfo.getPermissions()));
        }
        Collection<? extends GrantedAuthority> authorities
                = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
        SysUser user = userInfo.getSysUser();
        boolean enabled = StrUtil.equals(user.getLockFlag().toString(), USER_IS_LOCK);
        // 构造security用户
        return new XHuiUser(user.getUserId(), user.getPhone(), user.getTenantId(), user.getUsername(), user.getPassword(), enabled,
                true, true, enabled, authorities);
    }
}
