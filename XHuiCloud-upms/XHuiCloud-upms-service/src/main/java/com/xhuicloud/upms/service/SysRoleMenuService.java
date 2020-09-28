package com.xhuicloud.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhuicloud.upms.entity.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuService extends IService<SysRoleMenu> {


    Boolean saveRoleMenus(Integer roleId, String menuIds);

    Boolean deleteRoleMenus(List<Integer> ids);
}
