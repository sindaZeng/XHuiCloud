package com.xhuicloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.upms.entity.SysMenu;
import com.xhuicloud.upms.mapper.SysMenuMapper;
import com.xhuicloud.upms.service.SysMenuService;
import com.xhuicloud.upms.service.SysRoleMenuService;
import com.xhuicloud.upms.vo.MenuVO;
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
    public List<MenuVO> findMenuByRoleId(Integer roleId) {
        return baseMapper.listMenusByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveMenu(SysMenu sysMenu) {
        if (sysMenu.getType() == 0 && StringUtils.isEmpty(sysMenu.getPath())) {
            throw SysException.sysFail("类型为菜单时,路由路径不能为空!");
        } else if (sysMenu.getType() == 1 && StringUtils.isEmpty(sysMenu.getPermission())) {
            throw SysException.sysFail("类型为按钮时,授权标识不能为空!");
        } else if (sysMenu.getType() != 0 && sysMenu.getType() != 1) {
            throw SysException.sysFail("无效的类型!");
        }
        return save(sysMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteMenu(Integer id) {
        // 查询当前节点的节点
        SysMenu sysMenu = getOne(Wrappers.<SysMenu>query()
                .lambda().eq(SysMenu::getMenuId, id));
        if (ObjectUtils.isNotEmpty(sysMenu)) {
            List<Integer> ids = Lists.newArrayList();
            if (sysMenu.getIsDel() == 1) {
                ids.add(sysMenu.getMenuId());
                // 禁用逻辑
                // 查询当前节点的子节点 孙子节点 等等
                getChildMenus(ids, id);
                // 禁用菜单
                baseMapper.deleteMenu(ids);
                // 删除拥有这些菜单的角色
                sysRoleMenuService.deleteRoleMenus(ids);
            }else {
                // 启用逻辑
                sysMenu.setIsDel(1);
                updateById(sysMenu);
            }
            return true;
        } else {
            throw SysException.sysFail("没有此菜单或者按钮!");
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
    private void getChildMenus(List<Integer> menuList, Integer id) {
        List<Integer> childMenuList = list(Wrappers.<SysMenu>query()
                .lambda().eq(SysMenu::getParentId, id)).stream().map(SysMenu::getMenuId).collect(Collectors.toList());
        menuList.addAll(childMenuList);
        childMenuList.forEach(menu -> {
            getChildMenus(menuList, menu);
        });
    }
}
