package com.zsinda.fdp.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsinda.fdp.entity.SysConfig;
import com.zsinda.fdp.mapper.SysConfigMapper;
import com.zsinda.fdp.service.SysConfigService;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService{

    @Override
    public SysConfig getSysConfigByKey(String key) {
        return getOne(Wrappers.<SysConfig>lambdaQuery().eq(SysConfig::getConfigKey,key).eq(SysConfig::getDelFlag,1));
    }
}
