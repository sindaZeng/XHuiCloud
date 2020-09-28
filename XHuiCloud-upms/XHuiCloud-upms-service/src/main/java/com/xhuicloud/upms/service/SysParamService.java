package com.xhuicloud.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhuicloud.upms.entity.SysParam;

public interface SysParamService extends IService<SysParam>{

    SysParam getSysParamByKey(String sysUserDefaultPassword);

    Boolean saveParam(SysParam sysParam);
}
