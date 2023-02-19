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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.upms.entity.SysClientDetails;
import com.xhuicloud.upms.mapper.SysClientDetailsMapper;
import com.xhuicloud.upms.service.SysClientDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SysClientDetailsServiceImpl extends ServiceImpl<SysClientDetailsMapper, SysClientDetails> implements SysClientDetailsService {

    @Override
//    @Cacheable(value = CacheConstants.CLIENT_DETAILS, key = "#clientId", unless = "#result == null")
    public SysClientDetails getByIdOrClientId(String clientId) {
        return getOne(
                Wrappers.<SysClientDetails>lambdaQuery()
                        .eq(SysClientDetails::getClientId, clientId));
    }
}
