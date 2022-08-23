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
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xhuicloud.common.authorization.resource.constant.CustomAuthorizationGrantType;
import com.xhuicloud.common.core.constant.ThirdLoginUrlConstants;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.common.data.ttl.XHuiCommonThreadLocalHolder;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysSocial;
import com.xhuicloud.upms.entity.SysUser;
import com.xhuicloud.upms.entity.SysUserSocial;
import com.xhuicloud.upms.mapper.SysSocialMapper;
import com.xhuicloud.upms.mapper.SysUserSocialMapper;
import com.xhuicloud.upms.service.SysUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @program: XHuiCloud
 * @description: QQ登录
 * @author: Sinda
 * @create: 2020-06-17 15:01
 */
@Slf4j
@Component("qq")
@AllArgsConstructor
public class QqHandle extends AbstractSocialHandler {

    private final SysSocialMapper sysSocialMapper;

    private final SysUserSocialMapper sysUserSocialMapper;

    private final SysUserService sysUserService;

    @Override
    public SysSocial sysSocial(String type) {
        SysSocial sysSocial = new SysSocial();
        sysSocial.setType(type);
        return sysSocialMapper.selectOne(new QueryWrapper<>(sysSocial));
    }

    @Override
    public String getOpenId(SysSocial sysSocial, String code) {
        String result = HttpUtil.get(String.format(ThirdLoginUrlConstants.getTokenUrl
                , sysSocial.getAppId(), sysSocial.getAppSecret(), code, URLUtil.encode(sysSocial.getRedirectUrl())));
        String access_token = result.split("&")[0].split("=")[1];
        log.info("QQ return result:[{}]", result);
        result = HttpUtil.get(String.format(ThirdLoginUrlConstants.getOpenIdUrl, access_token));
        result = result.replace("callback(", "");
        result = result.replace(")", "");
        return access_token + "&" + sysSocial.getAppId() + "&" + JSONUtil.parseObj(result).get("openid").toString();
    }

    @Override
    public UserInfo info(String openId) {
        String[] param = openId.split("&");
        SysUserSocial sysUserSocial = new SysUserSocial();
        sysUserSocial.setUserOpenid(param[2]);
        sysUserSocial.setSocialType(type());
        SysUserSocial userSocial = sysUserSocialMapper.selectOne(new QueryWrapper<>(sysUserSocial));
        Integer userId;
        if (ObjectUtil.isNull(userSocial)) {
            String result = HttpUtil.get(String.format(ThirdLoginUrlConstants.getQqUserInfoUrl, param[0], param[1], param[2]));
            // 创建用户
            sysUserSocial.setUserId(sysUserService.saveUser(createDefaultUser(JSONUtil.parseObj(result))));
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
        return CustomAuthorizationGrantType.QQ.getValue();
    }

    @Override
    public SysUser createDefaultUser(Object obj) {
        JSONObject qqInfo = (JSONObject) obj;
        SysUser user = new SysUser();
        user.setUsername(qqInfo.getStr("nickname"));
        user.setAvatar(qqInfo.getStr("figureurl_qq"));
        user.setSex(StringUtils.equals("男", qqInfo.getStr("gender")) ? 1 : 0);
        user.setLockFlag(0);
        user.setTenantId(XHuiCommonThreadLocalHolder.getTenant());
        return user;
    }
}
