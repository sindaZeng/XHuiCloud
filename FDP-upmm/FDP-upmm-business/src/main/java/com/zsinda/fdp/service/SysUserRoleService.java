package com.zsinda.fdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 新增用户角色
     * @param userId
     * @param roleIds
     */
    void saveUserRole(Integer userId, List<Integer> roleIds);

    /**
     * 更新用户角色
     * @param userId
     * @param roleIds
     */
    void updateUserRole(Integer userId, List<Integer> roleIds);
}
