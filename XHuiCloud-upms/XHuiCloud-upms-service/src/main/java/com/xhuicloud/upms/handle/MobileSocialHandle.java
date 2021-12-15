package com.xhuicloud.upms.handle;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xhuicloud.common.data.tenant.XHuiTenantHolder;
import com.xhuicloud.upms.dto.UserInfo;
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
    public String getOpenId(String mobile) {
        return mobile;
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
    public Boolean check(String mobile) {
        //不校验
        return true;
    }

    @Override
    public SysUser createDefaultUser(Object obj) {
        SysUser user = new SysUser();
        user.setUsername((String) obj);
        user.setPhone((String) obj);
        user.setSex(1);
        user.setLockFlag(1);
        user.setTenantId(XHuiTenantHolder.getTenant());
        return user;
    }
}
