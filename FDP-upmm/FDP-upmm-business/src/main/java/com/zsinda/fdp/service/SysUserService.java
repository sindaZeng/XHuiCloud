package com.zsinda.fdp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.dto.UserDto;
import com.zsinda.fdp.dto.UserInfo;
import com.zsinda.fdp.entity.SysUser;

public interface SysUserService extends IService<SysUser>{

    /**
     * 查询用户信息
     *
     * @param sysUser 用户
     * @return userInfo
     */
    UserInfo findUserInfo(SysUser sysUser);

    /**
     * 分页查询用户信息
     *
     * @param page
     * @param userDto
     * @return
     */
    IPage userPage(Page page, UserDto userDto);

    Boolean updateUser(SysUser sysUser);
}
