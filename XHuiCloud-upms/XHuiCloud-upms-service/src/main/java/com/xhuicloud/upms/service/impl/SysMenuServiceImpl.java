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
import com.google.common.collect.Lists;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.upms.entity.SysMenu;
import com.xhuicloud.upms.mapper.SysMenuMapper;
import com.xhuicloud.upms.service.SysMenuService;
import com.xhuicloud.upms.service.SysRoleMenuService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenu> findMenuByRoleId(Long roleId) {
        return baseMapper.listMenusByRoleId(roleId);
    }

    @Override
    public List<SysMenu> findMenuByRoleCode(String roleCode) {
        return baseMapper.listMenusByRoleCode(roleCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveMenu(SysMenu sysMenu) {
        if (sysMenu.getType() == 0 && StringUtils.isEmpty(sysMenu.getPath())) {
            SysException.sysFail("类型为菜单时,路由路径不能为空!");
        } else if (sysMenu.getType() == 1 && StringUtils.isEmpty(sysMenu.getPermission())) {
            SysException.sysFail("类型为按钮时,授权标识不能为空!");
        } else if (sysMenu.getType() != 0 && sysMenu.getType() != 1) {
            SysException.sysFail("无效的类型!");
        }
        return save(sysMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteMenu(Long id) {
        // 查询当前节点的节点
        SysMenu sysMenu = getOne(Wrappers.<SysMenu>query()
                .lambda().eq(SysMenu::getId, id));
        if (ObjectUtils.isNotEmpty(sysMenu)) {
            List<Long> ids = Lists.newArrayList();
            ids.add(sysMenu.getId());
            getChildMenus(ids, id);
            removeByIds(ids);
            sysRoleMenuService.deleteRoleMenus(ids);
            return true;
        } else {
            throw SysException.fail("没有此菜单或者按钮!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateMenu(SysMenu sysMenu) {
        return updateById(sysMenu);
    }

    /**
     * 递归查询 自己 以及子级
     *
     * @param menuList
     * @param id
     */
    private void getChildMenus(List<Long> menuList, Long id) {
        List<Long> childMenuList = list(Wrappers.<SysMenu>query()
                .lambda().eq(SysMenu::getParentId, id)).stream().map(SysMenu::getId).collect(Collectors.toList());
        menuList.addAll(childMenuList);
        childMenuList.forEach(menu -> {
            getChildMenus(menuList, menu);
        });
    }
}
