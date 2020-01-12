package com.zsinda.fdp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.entity.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {


    List<SysRole> findRoleById(Integer userId);

    Boolean deleteRoleById(Integer id);
}

