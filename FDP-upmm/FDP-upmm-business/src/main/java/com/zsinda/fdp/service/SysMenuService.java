package com.zsinda.fdp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.entity.SysMenu;
import com.zsinda.fdp.vo.MenuVO;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    List<MenuVO> findMenuByRoleId(Integer roleId);

    /**
     * 新增菜单
     * @param sysMenu
     * @return
     */
    boolean saveMenu(SysMenu sysMenu);

    /**
     * 禁用时: 禁用本级，以及子级 ，以及禁用所有拥有此菜单以及子菜单的角色。
     * 启用时: 只启用本级。
     *
     * @param id
     * @return
     */
    boolean deleteMenu(Integer id);
}

