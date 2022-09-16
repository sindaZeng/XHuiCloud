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

import cn.binarywang.tools.generator.ChineseNameGenerator;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xhuicloud.common.authorization.resource.constant.CustomAuthorizationGrantType;
import com.xhuicloud.common.authorization.resource.constant.LoginPlatformEnum;
import com.xhuicloud.common.core.constant.SecurityConstants;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.common.data.ttl.XHuiCommonThreadLocalHolder;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysSocial;
import com.xhuicloud.upms.entity.SysUser;
import com.xhuicloud.upms.entity.SysUserSocial;
import com.xhuicloud.upms.mapper.SysUserSocialMapper;
import com.xhuicloud.upms.service.SysUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("WXMP")
@AllArgsConstructor
public class WeChatMplHandle extends AbstractSocialHandler {

    private final RedisTemplate redisTemplate;

    private final SysUserSocialMapper sysUserSocialMapper;

    private final SysUserService sysUserService;

    @Override
    public String getOpenId(SysSocial sysSocial, String code) {
        Object openId = redisTemplate.opsForValue().get(
                SecurityConstants.WECHAT_MP_SCAN_SUCCESS + code);
        if (openId == null) {
            throw SysException.sysFail(SysException.PARAM_EXCEPTION);
        }
        return openId.toString();
    }

    @Override
    public UserInfo info(String openId) {
        SysUserSocial sysUserSocial = new SysUserSocial();
        sysUserSocial.setUserOpenid(openId);
        sysUserSocial.setSocialType(type());
        SysUserSocial userSocial = sysUserSocialMapper.selectOne(Wrappers.lambdaQuery(sysUserSocial));
        Integer userId;
        if (ObjectUtil.isNull(userSocial)) {
            // 创建用户
            sysUserSocial.setUserId(sysUserService.saveUser(createDefaultUser(null)));
            // 绑定OpenId
            sysUserSocialMapper.insert(sysUserSocial);
            userId = sysUserSocial.getUserId();
        } else {
            userId = userSocial.getUserId();
        }
        SysUser user = sysUserService.getById(userId);
        if (ObjectUtil.isNull(user)) {
            throw SysException.sysFail(SysException.USER_NOT_EXIST_DATA_EXCEPTION);
        }
        return sysUserService.getSysUser(user);
    }

    @Override
    public String type() {
        return LoginPlatformEnum.WECHAT_MP.getType();
    }

    @Override
    public SysUser createDefaultUser(Object obj) {
        SysUser user = new SysUser();
        user.setUsername(ChineseNameGenerator.getInstance().generate());
        user.setSex(1);
        user.setLockFlag(0);
        user.setTenantId(XHuiCommonThreadLocalHolder.getTenant());
        return user;
    }
}
