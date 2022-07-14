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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.common.authorization.resource.social.SocialHandle;
import com.xhuicloud.common.core.constant.SysParamConstants;
import com.xhuicloud.common.core.constant.ThirdLoginUrlConstants;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.upms.entity.SysParam;
import com.xhuicloud.upms.entity.SysSocial;
import com.xhuicloud.upms.mapper.SysSocialMapper;
import com.xhuicloud.upms.service.SysSocialService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_ANONYMOUS_YES;


@Service
@AllArgsConstructor
public class SysSocialServiceImpl extends ServiceImpl<SysSocialMapper, SysSocial> implements SysSocialService {

    private final Map<String, SocialHandle> handle;

    @Override
    public UserInfo getSysUser(String type, String code) {
        return handle.get(type).handle(code);
    }

    @Override
    public Boolean updateSocialToken(String type) {
        List<SysSocial> sysSocials = list(Wrappers.<SysSocial>lambdaQuery().eq(SysSocial::getType, type));
        if (CollectionUtil.isNotEmpty(sysSocials)) {
            for (SysSocial sysSocial : sysSocials) {
                String url = String.format(ThirdLoginUrlConstants.MINI_WECHAT_ACCESS_TOKEN, sysSocial.getAppId(), sysSocial.getAppSecret());
                String result = HttpUtil.get(url);
                JSONObject response = JSONUtil.parseObj(result);
                String access_token = response.getStr("access_token");
                sysSocial.setAppAccessToken(access_token);
            }
        }
        return updateBatchById(sysSocials);
    }
}
