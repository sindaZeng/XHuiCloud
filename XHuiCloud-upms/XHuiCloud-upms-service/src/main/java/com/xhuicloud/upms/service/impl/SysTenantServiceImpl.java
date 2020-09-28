package com.xhuicloud.upms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.upms.entity.SysTenant;
import com.xhuicloud.upms.mapper.SysTenantMapper;
import com.xhuicloud.upms.service.SysTenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteTenant(Integer id) {
        SysTenant sysTenant = getById(id);
        if (ObjectUtil.isEmpty(sysTenant)) {
            throw SysException.sysFail(SysException.TENANT_NOT_EXIST_DATA_EXCEPTION);
        }
        if (sysTenant.getIsDel() == 1) {
            sysTenant.setIsDel(0);
        } else {
            sysTenant.setIsDel(1);
        }
        return updateById(sysTenant);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean state(Integer id, Integer state) {
        SysTenant sysTenant = getById(id);
        if (ObjectUtil.isEmpty(sysTenant)) {
            throw SysException.sysFail(SysException.TENANT_NOT_EXIST_DATA_EXCEPTION);
        }
        sysTenant.setState(state);
        return updateById(sysTenant);
    }
}
