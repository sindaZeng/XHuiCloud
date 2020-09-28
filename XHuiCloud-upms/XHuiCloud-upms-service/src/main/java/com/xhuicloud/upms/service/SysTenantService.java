package com.xhuicloud.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhuicloud.upms.entity.SysTenant;

public interface SysTenantService extends IService<SysTenant> {

    Boolean deleteTenant(Integer id);

    Boolean state(Integer id, Integer state);
}
