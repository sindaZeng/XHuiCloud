package com.zsinda.fdp.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zsinda.fdp.annotation.Inner;
import com.zsinda.fdp.entity.SysUser;
import com.zsinda.fdp.service.SysUserService;
import com.zsinda.fdp.utils.R;
import com.zsinda.fdp.utils.SpringSecurityUtils;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Api(value = "user",tags = "用户管理模块")
public class SysUserController {

    private final SysUserService sysUserService;

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

    /**
     * 添加用户
     * @return
     */
    @Inner
    @PostMapping
    public R user() {
        SysUser sysUser = new SysUser();
        sysUser.setUsername("root");
        sysUser.setPhone("123123123");
        sysUser.setEmail("12332@ee.com");
        sysUser.setPassword("12312312312");
        return R.ok(sysUserService.save(sysUser));
    }
}
