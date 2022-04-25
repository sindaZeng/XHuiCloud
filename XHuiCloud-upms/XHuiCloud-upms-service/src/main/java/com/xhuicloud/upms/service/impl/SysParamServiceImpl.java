/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.upms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.upms.entity.SysParam;
import com.xhuicloud.upms.mapper.SysParamMapper;
import com.xhuicloud.upms.service.SysParamService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper, SysParam> implements SysParamService {

    @Override
    @Cacheable(value = CacheConstants.SYS_PARAM, key = "#key", unless = "#result == null")
    public SysParam getSysParamByKey(String key) {
        return getOne(Wrappers.<SysParam>lambdaQuery().eq(SysParam::getParamKey,key));
    }

    @Override
    public List<SysParam> sysParamByKeyLike(String key) {
        return list(Wrappers.<SysParam>lambdaQuery().like(SysParam::getParamKey,key));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveParam(SysParam sysParam) {
        if (ObjectUtil.isNotNull(getOne(Wrappers.<SysParam>lambdaQuery()
                .eq(SysParam::getParamKey,sysParam.getParamKey())))){
            throw SysException.sysFail(SysException.PARAM_IS_EXIST_DATA_EXCEPTION);
        }
        return save(sysParam);
    }
}
