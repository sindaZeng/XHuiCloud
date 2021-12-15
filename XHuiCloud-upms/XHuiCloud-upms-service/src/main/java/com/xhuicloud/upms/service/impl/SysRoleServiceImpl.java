package com.xhuicloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.upms.entity.SysRole;
import com.xhuicloud.upms.entity.SysRoleMenu;
import com.xhuicloud.upms.mapper.SysRoleMapper;
import com.xhuicloud.upms.mapper.SysRoleMenuMapper;
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
    @Cacheable(value = CacheConstants.SYS_ROLE, unless = "#result == null")
    public List<Integer> getAllRoleIds() {
        return list(Wrappers.emptyWrapper()).stream().map(SysRole::getId).collect(Collectors.toList());
    }

}
