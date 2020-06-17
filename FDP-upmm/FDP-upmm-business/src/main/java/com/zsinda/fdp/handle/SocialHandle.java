package com.zsinda.fdp.handle;

import com.zsinda.fdp.dto.UserInfo;
import com.zsinda.fdp.entity.SysUser;

/**
 * @program: FDPlatform
 * @description: SocialHandle
 * @author: Sinda
 * @create: 2020-06-17 14:32
 */
public interface SocialHandle {

    /**
     * 处理方法
     *
     * @param loginStr 登录标识
     * @return
     */
    UserInfo handle(String loginStr);


    /**
     * 通过授权码获取 openId
     *
     * @param auth_code
     * @return
     */
    String getOpenId(String auth_code);

    /**
     * 通过 openId 获取用户信息
     *
     * @param openId
     * @return
     */
    UserInfo info(String openId);

    /**
     * 校验授权码，由每个渠道登录校验
     *
     * @param auth_code
     * @return
     */
    Boolean check(String auth_code);

    /**
     * 没有此用户的话。
     * 1、直接注册
     * 2、返回空，在页面上引导注册
     *
     * @param obj
     * @return
     */
    SysUser createDefaultUser(Object obj);

}
