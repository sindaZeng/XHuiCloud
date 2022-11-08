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

package com.xhuicloud.wechat.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.common.core.constant.ThirdLoginUrlConstants;
import com.xhuicloud.wechat.entity.WeChatAccount;
import com.xhuicloud.wechat.mapper.WeChatAccountMapper;
import com.xhuicloud.wechat.service.WeChatAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @program: wechat
* @description: 公众号账户
* @author: Sinda
* @create: 2022-11-04 17:05:04
*/
@Service
public class WeChatAccountServiceImpl extends ServiceImpl<WeChatAccountMapper, WeChatAccount> implements WeChatAccountService {

    @Override
    public Boolean updateWechatToken() {
        List<WeChatAccount> weChatAccounts = list();
        if (CollectionUtil.isNotEmpty(weChatAccounts)) {
            for (WeChatAccount weChatAccount : weChatAccounts) {
                String url = String.format(ThirdLoginUrlConstants.MINI_WECHAT_ACCESS_TOKEN, weChatAccount.getAppId(), weChatAccount.getAppSecret());
                String result = HttpUtil.get(url);
                JSONObject response = JSONUtil.parseObj(result);
                String access_token = response.getStr("access_token");
                weChatAccount.setAppAccessToken(access_token);
            }
        }
        return updateBatchById(weChatAccounts);
    }

}

