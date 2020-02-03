package com.zsinda.fdp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.entity.SysRoleMenu;

public interface SysRoleMenuService extends IService<SysRoleMenu> {


    Boolean saveRoleMenus(Integer roleId, String menuIds);
}
