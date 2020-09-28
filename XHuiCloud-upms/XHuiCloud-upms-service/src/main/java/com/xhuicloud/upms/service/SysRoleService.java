package com.xhuicloud.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhuicloud.upms.entity.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    /**
     * 通过用户id 查找角色
     * @param userId
     * @return
     */
    List<SysRole> findRoleById(Integer userId);

    /**
     * 通过角色id 删除
     * @param id
     * @return
     */
    Boolean deleteRoleById(Integer id);

    /**
     * 获取所有部门id
     * @return
     */
    List<Integer> getAllRoleIds();

}

