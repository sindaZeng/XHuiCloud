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

package com.xhuicloud.common.authorization.repository;

import com.xhuicloud.common.authorization.resource.userdetails.XHuiUserDetailsService;
import com.xhuicloud.common.core.constant.CacheConstants;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @program: XHuiCloud
 * @description: XHuiUserDetailsServiceImpl
 * @author: Sinda
 * @create: 2019-12-26 00:12
 **/
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    private final CacheManager cacheManager;

    private final XHuiUserDetailsService xHuiUserDetailsService;

    /**
     * 用户名登录
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Cache cache = cacheManager.getCache(CacheConstants.SYS_USER);
        if (cache != null && cache.get(username) != null) {
            return cache.get(username, UserDetails.class);
        }
        UserDetails userDetails = xHuiUserDetailsService.getUserDetails(username);
        cache.put(username, userDetails);
        return userDetails;
    }

}
