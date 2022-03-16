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

package com.xhuicloud.upms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhuicloud.upms.dto.UserDto;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysUser;

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
