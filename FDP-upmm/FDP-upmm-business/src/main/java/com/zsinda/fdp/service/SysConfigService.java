package com.zsinda.fdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.entity.SysConfig;

public interface SysConfigService extends IService<SysConfig>{

    SysConfig getSysConfigByKey(String sysUserDefaultPassword);

}
