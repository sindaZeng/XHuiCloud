package com.zsinda.fdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.entity.SysParam;

public interface SysParamService extends IService<SysParam>{

    SysParam getSysConfigByKey(String sysUserDefaultPassword);

    Boolean saveParam(SysParam sysParam);
}
