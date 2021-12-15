package com.xhuicloud.upms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.common.security.utils.SecurityHolder;
import com.xhuicloud.upms.entity.SysParam;
import com.xhuicloud.upms.mapper.SysParamMapper;
import com.xhuicloud.upms.service.SysParamService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper, SysParam> implements SysParamService {

    @Override
    @Cacheable(value = CacheConstants.SYS_PARAM, key = "#key", unless = "#result == null")
    public SysParam getSysParamByKey(String key) {
        return getOne(Wrappers.<SysParam>lambdaQuery().eq(SysParam::getParamKey,key));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveParam(SysParam sysParam) {
        if (ObjectUtil.isNotNull(getOne(Wrappers.<SysParam>lambdaQuery()
                .eq(SysParam::getParamKey,sysParam.getParamKey())))){
            throw SysException.sysFail(SysException.PARAM_IS_EXIST_DATA_EXCEPTION);
        }
        sysParam.setCreateId(SecurityHolder.getUserId());
        return save(sysParam);
    }
}
