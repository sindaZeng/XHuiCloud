package com.zsinda.fdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.entity.SysDept;

import java.util.List;

public interface SysDeptService extends IService<SysDept>{

    /**
     * 获取所有部门id
     * @return
     */
    List<Integer> getAllDeptIds();

    Boolean saveDept(SysDept sysDept);
}
