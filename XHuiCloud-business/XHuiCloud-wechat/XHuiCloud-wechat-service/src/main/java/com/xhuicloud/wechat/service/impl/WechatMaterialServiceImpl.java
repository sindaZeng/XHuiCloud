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

import com.xhuicloud.wechat.config.WeChatMpCommonService;
import com.xhuicloud.wechat.service.WechatMaterialService;
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: wechat
 * @description:
 * @author: Sinda
 * @create: 2022-11-14 21:29:18
 */
@Service
public class WechatMaterialServiceImpl implements WechatMaterialService {

    @Async
    @Override
    @SneakyThrows
    public void delete(String appId, List<String> mediaIds) {
        WxMpService wxMpService = WeChatMpCommonService.getWxMpService(appId);
        WxMpMaterialService materialService = wxMpService.getMaterialService();
        for (String mediaId : mediaIds) {
            boolean b = materialService.materialDelete(mediaId);
            System.out.println(b);
        }
    }
}

