package com.xhuicloud.upms.handle.login;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xhuicloud.common.authorization.resource.constant.CustomAuthorizationGrantType;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysSocial;
import com.xhuicloud.upms.entity.SysUser;
import com.xhuicloud.upms.service.SysUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("password")
@AllArgsConstructor
public class UsernamePasswordHandle extends AbstractSocialHandler {

    private final SysUserService sysUserService;

    @Override
    public String getOpenId(SysSocial sysSocial, String code) {
        return code;
    }

    @Override
    public UserInfo info(String openId) {
        SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda()
                .eq(SysUser::getUsername, openId).or().eq(SysUser::getPhone, openId));
        if (user == null) {
            return null;
        }
        return sysUserService.getSysUser(user);
    }

    @Override
    public String type() {
        return CustomAuthorizationGrantType.PASSWORD.getValue();
    }

    @Override
    public SysUser createDefaultUser(Object obj) {
        return null;
    }
}
