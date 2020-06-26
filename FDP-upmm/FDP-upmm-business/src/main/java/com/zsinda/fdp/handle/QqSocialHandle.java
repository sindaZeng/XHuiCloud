package com.zsinda.fdp.handle;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsinda.fdp.constant.ThirdLoginUrlConstants;
import com.zsinda.fdp.dto.UserInfo;
import com.zsinda.fdp.entity.SysSocial;
import com.zsinda.fdp.entity.SysUser;
import com.zsinda.fdp.entity.SysUserSocial;
import com.zsinda.fdp.enums.login.LoginTypeEnum;
import com.zsinda.fdp.exception.SysException;
import com.zsinda.fdp.mapper.SysSocialMapper;
import com.zsinda.fdp.mapper.SysUserSocialMapper;
import com.zsinda.fdp.service.SysUserService;
import com.zsinda.fdp.tenant.FdpTenantHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @program: FDPlatform
 * @description: QQ登录
 * @author: Sinda
 * @create: 2020-06-17 15:01
 */
@Slf4j
@Component("QQ")
@AllArgsConstructor
public class QqSocialHandle extends AbstractSocialHandle {

    private final SysSocialMapper sysSocialMapper;

    private final SysUserSocialMapper sysUserSocialMapper;

    private final SysUserService sysUserService;

    @Override
    public String getOpenId(String auth_code) {
        SysSocial sysSocial = new SysSocial();
        sysSocial.setType(LoginTypeEnum.QQ.name());
        sysSocial = sysSocialMapper.selectOne(new QueryWrapper<>(sysSocial));
        String result = HttpUtil.get(String.format(ThirdLoginUrlConstants.getTokenUrl
                , sysSocial.getAppId(), sysSocial.getAppSecret(), auth_code, URLUtil.encode(sysSocial.getRedirectUrl())));
        String access_token = result.split("&")[0].split("=")[1];
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
        sysUserSocial.setSocialType(LoginTypeEnum.QQ.name());
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
    public Boolean check(String auth_code) {
        //不校验
        return true;
    }

    @Override
    public SysUser createDefaultUser(Object obj) {
        JSONObject qqInfo = (JSONObject) obj;
        SysUser user = new SysUser();
        user.setUsername(qqInfo.getStr("nickname"));
        user.setAvatar(qqInfo.getStr("figureurl_qq"));
        user.setSex(StringUtils.equals("男", qqInfo.getStr("gender")) ? 1 : 0);
        user.setLockFlag(1);
        user.setTenantId(FdpTenantHolder.getTenant());
        return user;
    }
}
