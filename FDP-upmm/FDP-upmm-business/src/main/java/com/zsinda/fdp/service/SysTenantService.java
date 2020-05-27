package com.zsinda.fdp.service;

import com.zsinda.fdp.entity.SysTenant;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SysTenantService extends IService<SysTenant>{

    Boolean deleteTenant(Integer id);

    Boolean state(Integer id, Integer state);
}
