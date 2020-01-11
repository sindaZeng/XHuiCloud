package com.zsinda.fdp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.entity.SysMenu;
import com.zsinda.fdp.vo.MenuVO;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    List<MenuVO> findMenuByRoleId(Integer roleId);

}

