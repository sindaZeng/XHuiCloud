package com.xhuicloud.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhuicloud.upms.entity.SysDept;
import com.xhuicloud.upms.vo.DeptVo;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {

    /**
     * 获取所有部门id
     * @return
     */
    List<Integer> getAllDeptIds();

    Boolean saveDept(SysDept sysDept);

    Boolean deleteDept(Integer id);

    Boolean updateDept(SysDept sysDept);

    List<List<Integer>> getDeptTree(List<DeptVo> deptVos);
}
