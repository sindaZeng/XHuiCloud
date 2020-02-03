package com.zsinda.fdp.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.zsinda.fdp.dto.UserInfo;
import com.zsinda.fdp.entity.SysUser;
import com.zsinda.fdp.feign.SysUserServiceFeign;
import com.zsinda.fdp.service.FdpUserDetailsService;
import com.zsinda.fdp.utils.R;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.zsinda.fdp.constant.AuthorizationConstants.*;

/**
 * @program: FDPlatform
 * @description: FdpUserDetailsServiceImpl
 * @author: Sinda
 * @create: 2019-12-26 00:12
 **/
@AllArgsConstructor
public class FdpUserDetailsServiceImpl implements FdpUserDetailsService {

    private final SysUserServiceFeign sysUserServiceFeign;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        R<UserInfo> info = sysUserServiceFeign.getSysUser(userName,IS_COMMING_INNER_YES);
        UserInfo userInfo = info.getData();
        return getUserDetails(userInfo);
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
        boolean enabled = StrUtil.equals(user.getLockFlag().toString(),USER_IS_LOCK);
        // 构造security用户
        return new FdpUser(user.getUserId(), user.getUsername(), user.getPassword(), enabled,
                true, true, !USER_IS_LOCK.equals(user.getLockFlag()), authorities);
    }

    @Override
    public UserDetails loadUserBySocial(String code) throws UsernameNotFoundException {
        return null;
    }
}
