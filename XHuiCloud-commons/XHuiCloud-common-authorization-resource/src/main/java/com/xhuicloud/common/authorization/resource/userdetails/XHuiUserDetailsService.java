package com.xhuicloud.common.authorization.resource.userdetails;

import com.xhuicloud.upms.dto.UserInfo;
import org.springframework.security.core.userdetails.UserDetails;

public interface XHuiUserDetailsService {

    UserDetails getUserDetails(String username);

    UserDetails getUserDetails(UserInfo userInfo);

}
