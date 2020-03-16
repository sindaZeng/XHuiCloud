package com.zsinda.fdp.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsinda.fdp.entity.SysRoleMenu;
import com.zsinda.fdp.mapper.SysRoleMenuMapper;
import com.zsinda.fdp.service.SysRoleMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveRoleMenus(Integer roleId, String menuIds) {
        this.remove(Wrappers.<SysRoleMenu>query().lambda()
                .eq(SysRoleMenu::getRoleId, roleId));
        if (StringUtils.isEmpty(menuIds)) {
            return Boolean.TRUE;
        }
        List<SysRoleMenu> roleMenuList = Arrays
                .stream(menuIds.split(","))
                .map(menuId -> {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(roleId);
                    roleMenu.setMenuId(Integer.valueOf(menuId));
                    return roleMenu;
                }).collect(Collectors.toList());
        return saveBatch(roleMenuList);
    }

    @Override
    @Transactional
    public Boolean deleteRoleMenus(List<Integer> ids) {
        return baseMapper.deleteRoleMenus(ids);
    }
}
