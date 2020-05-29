package com.zsinda.fdp.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsinda.fdp.entity.SysParam;
import com.zsinda.fdp.mapper.SysParamMapper;
import com.zsinda.fdp.service.SysParamService;
import org.springframework.stereotype.Service;

@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper, SysParam> implements SysParamService {

    @Override
    public SysParam getSysConfigByKey(String key) {
        return getOne(Wrappers.<SysParam>lambdaQuery().eq(SysParam::getConfigKey,key).eq(SysParam::getDelFlag,1));
    }
}
