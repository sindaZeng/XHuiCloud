package com.zsinda.fdp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.dto.UserDto;
import com.zsinda.fdp.dto.UserInfo;
import com.zsinda.fdp.entity.SysUser;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    /**
     * 查询用户信息
     *
     * @param sysUser 用户
     * @return userInfo
     */
    UserInfo getSysUser(SysUser sysUser);

    /**
     * 分页查询用户信息
     *
     * @param page
     * @param userDto
     * @return
     */
    IPage userPage(Page page, UserDto userDto);

    /**
     * 编辑用户
     *
     * @param sysUser
     * @return
     */
    Boolean updateUser(SysUser sysUser);

    /**
     * 开启/禁用用户
     *
     * @param id
     * @return
     */
    Boolean deleteUser(Integer id);

    /**
     * 锁定/解锁用户
     *
     * @param id
     * @return
     */
    Boolean lock(Integer id);

    /**
     * 导入用户
     *
     * @param userList      用户集合
     * @param updateSupport 是否更新已存在的用户
     * @return
     */
    String importUser(List<SysUser> userList, boolean updateSupport);

    /**
     * 创建用户
     *
     * @param sysUser
     * @return
     */
    Integer saveUser(SysUser sysUser);
}
