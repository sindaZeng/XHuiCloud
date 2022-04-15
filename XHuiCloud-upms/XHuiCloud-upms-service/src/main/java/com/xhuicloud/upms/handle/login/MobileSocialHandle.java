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

package com.xhuicloud.upms.handle.login;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xhuicloud.common.core.enums.login.LoginTypeEnum;
import com.xhuicloud.common.data.ttl.XHuiCommonThreadLocalHolder;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysSocial;
import com.xhuicloud.upms.entity.SysUser;
import com.xhuicloud.upms.service.SysUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: XHuiCloud
 * @description: 手机短信登录
 * @author: Sinda
 * @create: 2020-06-17 15:01
 */
@Slf4j
@Component("SMS")
@AllArgsConstructor
public class MobileSocialHandle extends AbstractSocialHandle {

    private final SysUserService sysUserService;

    @Override
    public String getOpenId(SysSocial sysSocial, String code) {
        return code;
    }

    @Override
    public UserInfo info(String mobile) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, mobile);
        SysUser user = sysUserService.getOne(queryWrapper);
        if (ObjectUtil.isNull(user)) {
            sysUserService.saveUser(createDefaultUser(mobile));
            user = sysUserService.getOne(queryWrapper);
        }
        return sysUserService.getSysUser(user);
    }

    @Override
    public String type() {
        return LoginTypeEnum.SMS.getType();
    }

    @Override
    public SysUser createDefaultUser(Object obj) {
        SysUser user = new SysUser();
        user.setUsername((String) obj);
        user.setPhone((String) obj);
        user.setSex(1);
        user.setLockFlag(1);
        user.setTenantId(XHuiCommonThreadLocalHolder.getTenant());
        return user;
    }
}
