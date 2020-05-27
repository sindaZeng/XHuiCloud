package com.zsinda.fdp.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsinda.fdp.entity.SysUserRole;
import com.zsinda.fdp.mapper.SysUserRoleMapper;
import com.zsinda.fdp.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    @Transactional
    public void saveUserRole(Integer userId, List<Integer> roleIds) {
        List<SysUserRole> list = new ArrayList();
        for (Integer roleId : roleIds) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            saveBatch(list);
        }
    }

    @Override
    @Transactional
    public void updateUserRole(Integer userId, List<Integer> roleIds) {
        remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId,userId));
        saveUserRole(userId, roleIds);
    }
}
