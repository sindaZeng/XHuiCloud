package com.xhuicloud.common.authorization.resource.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

/**
 * @Desc 其他用户体系
 * @Author Sinda
 * @Date 2022/9/16
 */
public class XHuiOtherUserDetailsServiceImpl implements XHuiUserDetailsService {

    @Override
    public UserDetails getUserDetails(Map<String, Object> claims) {
        return null;
    }

    @Override
    public UserDetails loadUserBySocial(String username, String grantType) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
