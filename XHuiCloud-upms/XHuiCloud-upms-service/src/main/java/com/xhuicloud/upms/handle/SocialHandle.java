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

package com.xhuicloud.upms.handle;

import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysUser;

/**
 * @program: XHuiCloud
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
