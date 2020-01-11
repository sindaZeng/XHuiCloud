package com.zsinda.fdp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @program: FDPlatform
 * @description: FdpUserDetailsService
 * @author: Sinda
 * @create: 2019-12-26 00:11
 **/
public interface FdpUserDetailsService extends UserDetailsService {


    UserDetails loadUserBySocial(String code) throws UsernameNotFoundException;


}
