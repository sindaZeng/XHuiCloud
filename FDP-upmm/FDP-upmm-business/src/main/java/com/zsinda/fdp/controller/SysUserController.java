package com.zsinda.fdp.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zsinda.fdp.annotation.Inner;
import com.zsinda.fdp.entity.SysUser;
import com.zsinda.fdp.service.SysUserService;
import com.zsinda.fdp.utils.R;
import com.zsinda.fdp.utils.SpringSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取当前登录用户全部信息
     */
    @GetMapping("/info")
    public R info() {
        String username = SpringSecurityUtils.getUser().getUsername();
        SysUser user = sysUserService.getOne(Wrappers.<SysUser>query()
                .lambda().eq(SysUser::getUsername, username));
        if (user == null) {
            return R.failed(null, "获取当前用户信息失败");
        }
        return R.ok(sysUserService.findUserInfo(user));
    }

    @Inner
    @GetMapping("/getSysUser/{userName}")
    public R getSysUser(@PathVariable String userName){

        SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, userName));
        if (user == null) {
            return R.failed(null, String.format("用户信息为空 %s", userName));
        }
        return R.ok(sysUserService.findUserInfo(user));
    }
}
