package com.zsinda.fdp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsinda.fdp.entity.SysRole;
import com.zsinda.fdp.mapper.SysRoleMapper;
import com.zsinda.fdp.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> findRoleById(Integer userId) {
        return baseMapper.listRolesByUserId(userId);
    }
}
