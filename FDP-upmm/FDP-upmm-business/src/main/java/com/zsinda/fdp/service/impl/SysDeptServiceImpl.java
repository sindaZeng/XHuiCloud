package com.zsinda.fdp.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsinda.fdp.entity.SysDept;
import com.zsinda.fdp.mapper.SysDeptMapper;
import com.zsinda.fdp.service.SysDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    public List<Integer> getAllDeptIds() {
        return list(Wrappers.emptyWrapper()).stream().map(SysDept::getDeptId).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDept(SysDept sysDept) {
        return save(sysDept);
    }

}
