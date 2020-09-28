package com.xhuicloud.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @program: XHuiCloud
 * @description: FdpUserDetailsService
 * @author: Sinda
 * @create: 2019-12-26 00:11
 **/
public interface XHuiUserDetailsService extends UserDetailsService {

    UserDetails loadUserBySocial(String code) throws UsernameNotFoundException;
}
