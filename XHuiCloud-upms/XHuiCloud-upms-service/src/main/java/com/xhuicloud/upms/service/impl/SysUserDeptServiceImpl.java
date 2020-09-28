package com.xhuicloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.upms.entity.SysUserDept;
import com.xhuicloud.upms.mapper.SysUserDeptMapper;
import com.xhuicloud.upms.service.SysUserDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserDeptServiceImpl extends ServiceImpl<SysUserDeptMapper, SysUserDept> implements SysUserDeptService {

    @Override
    @Transactional
    public void saveUserDept(Integer userId, List<Integer> deptIds) {
        List<SysUserDept> list = new ArrayList();
        for (Integer deptId : deptIds) {
            SysUserDept ud = new SysUserDept();
            ud.setUserId(userId);
            ud.setDeptId(deptId);
            list.add(ud);
        }
        if (list.size() > 0) {
            saveBatch(list);
        }
    }

    @Override
    @Transactional
    public void updateUserDept(Integer userId, List<Integer> deptIds) {
        remove(Wrappers.<SysUserDept>lambdaQuery().eq(SysUserDept::getUserId,userId));
        saveUserDept(userId,deptIds);
    }
}
