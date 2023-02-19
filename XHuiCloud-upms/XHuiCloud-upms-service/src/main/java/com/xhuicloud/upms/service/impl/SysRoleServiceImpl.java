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

package com.xhuicloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.upms.entity.SysRole;
import com.xhuicloud.upms.entity.SysRoleMenu;
import com.xhuicloud.upms.entity.SysUserRole;
import com.xhuicloud.upms.mapper.SysRoleMapper;
import com.xhuicloud.upms.mapper.SysRoleMenuMapper;
import com.xhuicloud.upms.mapper.SysUserRoleMapper;
import com.xhuicloud.upms.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMenuMapper sysRoleMenuMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysRole> findRoleById(Long userId) {
        return baseMapper.listRolesByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteRoleById(Long id) {
        removeById(id);
        sysUserRoleMapper.delete(Wrappers
                .<SysUserRole>update().lambda()
                .eq(SysUserRole::getRoleId, id));
        sysRoleMenuMapper.delete(Wrappers
                .<SysRoleMenu>update().lambda()
                .eq(SysRoleMenu::getRoleId, id));
        return Boolean.TRUE;
    }

    @Override
    @Cacheable(value = CacheConstants.SYS_ROLE, unless = "#result == null")
    public List<Long> getAllRoleIds() {
        return list(Wrappers.emptyWrapper()).stream().map(SysRole::getId).collect(Collectors.toList());
    }

}
