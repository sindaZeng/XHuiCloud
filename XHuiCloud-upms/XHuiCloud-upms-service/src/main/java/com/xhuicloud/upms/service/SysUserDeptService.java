package com.xhuicloud.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhuicloud.upms.entity.SysUserDept;

import java.util.List;

public interface SysUserDeptService extends IService<SysUserDept> {

    /**
     * 新增用户部门
     *
     * @param userId
     * @param deptIds
     */
    void saveUserDept(Integer userId, List<Integer> deptIds);

    /**
     * 更新用户部门
     *
     * @param userId
     * @param deptIds
     */
    void updateUserDept(Integer userId, List<Integer> deptIds);
}
