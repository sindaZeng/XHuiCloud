package com.zsinda.fdp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsinda.fdp.entity.SysMenu;
import com.zsinda.fdp.mapper.SysMenuMapper;
import com.zsinda.fdp.service.SysMenuService;
import com.zsinda.fdp.vo.MenuVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<MenuVO> findMenuByRoleId(Integer roleId) {
        return baseMapper.listMenusByRoleId(roleId);
    }
}
