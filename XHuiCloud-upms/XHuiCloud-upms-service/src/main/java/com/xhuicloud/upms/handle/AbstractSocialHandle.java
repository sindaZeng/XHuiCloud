package com.xhuicloud.upms.handle;

import com.xhuicloud.upms.dto.UserInfo;

/**
 * @program: XHuiCloud
 * @description: AbstractSocialHandle
 * @author: Sinda
 * @create: 2020-06-17 14:35
 */
public abstract class AbstractSocialHandle implements SocialHandle {

    @Override
    public UserInfo handle(String auth_code) {
        if (!check(auth_code)) {
            return null;
        }
        return info(getOpenId(auth_code));
    }
}
