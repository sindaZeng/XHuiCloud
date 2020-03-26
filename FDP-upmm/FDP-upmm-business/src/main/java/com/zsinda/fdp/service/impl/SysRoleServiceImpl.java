package com.zsinda.fdp.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsinda.fdp.entity.SysRole;
import com.zsinda.fdp.entity.SysRoleMenu;
import com.zsinda.fdp.mapper.SysRoleMapper;
import com.zsinda.fdp.mapper.SysRoleMenuMapper;
import com.zsinda.fdp.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysRole> findRoleById(Integer userId) {
        return baseMapper.listRolesByUserId(userId);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteRoleById(Integer id) {
        sysRoleMenuMapper.delete(Wrappers
                .<SysRoleMenu>update().lambda()
                .eq(SysRoleMenu::getRoleId, id));
        return removeById(id);
    }

    @Override
    public List<Integer> getAllRoleIds() {
        return list(Wrappers.emptyWrapper()).stream().map(SysRole::getRoleId).collect(Collectors.toList());
    }

}
