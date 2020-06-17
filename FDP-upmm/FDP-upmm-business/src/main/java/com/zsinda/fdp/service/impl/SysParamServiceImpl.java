package com.zsinda.fdp.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsinda.fdp.constant.CacheConstants;
import com.zsinda.fdp.entity.SysParam;
import com.zsinda.fdp.exception.SysException;
import com.zsinda.fdp.mapper.SysParamMapper;
import com.zsinda.fdp.service.SysParamService;
import com.zsinda.fdp.utils.SpringSecurityUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper, SysParam> implements SysParamService {

    @Override
    @Cacheable(value = CacheConstants.SYSPARAM, key = "#key", unless = "#result == null")
    public SysParam getSysParamByKey(String key) {
        return getOne(Wrappers.<SysParam>lambdaQuery().eq(SysParam::getParamKey,key).eq(SysParam::getDelFlag,1));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveParam(SysParam sysParam) {
        if (ObjectUtil.isNotNull(getOne(Wrappers.<SysParam>lambdaQuery()
                .eq(SysParam::getParamKey,sysParam.getParamKey())))){
            throw SysException.sysFail(SysException.PARAM_IS_EXIST_DATA_EXCEPTION);
        }
        sysParam.setCreateId(SpringSecurityUtils.getUserId());
        return save(sysParam);
    }
}
