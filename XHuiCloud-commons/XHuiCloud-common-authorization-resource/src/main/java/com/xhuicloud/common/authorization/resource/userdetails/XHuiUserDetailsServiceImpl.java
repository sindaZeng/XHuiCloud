package com.xhuicloud.common.authorization.resource.userdetails;

import cn.hutool.core.util.ArrayUtil;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysUser;
import com.xhuicloud.upms.feign.SysUserServiceFeign;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.*;

@AllArgsConstructor
public class XHuiUserDetailsServiceImpl implements XHuiUserDetailsService {

    private final SysUserServiceFeign sysUserServiceFeign;

    @Override
    public UserDetails getUserDetails(String username) {
        return getUserDetails(sysUserServiceFeign.getSysUser(username, IS_COMMING_ANONYMOUS_YES).getData());
    }

    @Override
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
