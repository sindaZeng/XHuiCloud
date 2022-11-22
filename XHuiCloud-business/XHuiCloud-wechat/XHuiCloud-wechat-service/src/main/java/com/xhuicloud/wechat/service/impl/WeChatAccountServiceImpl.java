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

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.wechat.config.WeChatMpCommonService;
import com.xhuicloud.wechat.entity.WeChatAccount;
import com.xhuicloud.wechat.mapper.WeChatAccountMapper;
import com.xhuicloud.wechat.service.WeChatAccountService;
import com.xhuicloud.wechat.vo.WeChatSummaryVo;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserSummary;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public WeChatSummaryVo getUserSummary(String appId) {
        WxMpService wxMpService = WeChatMpCommonService.getWxMpService(appId);
        WeChatAccount weChatAccount = getOne(Wrappers.<WeChatAccount>lambdaQuery().eq(WeChatAccount::getAppId, appId));
        Integer totalUser = 0;
        Integer newUser = 0;
        Integer cancelUser = 0;
        try {
            WxMpUserList wxUserList = wxMpService.getUserService().userList(null);
            totalUser = wxUserList.getCount();
            Date today = DateUtil.date().toJdkDate();
            Date beginDate = DateUtil.offsetDay(today, -7).toJdkDate();
            Date endDate = DateUtil.offsetDay(today, -1).toJdkDate();
            List<WxDataCubeUserSummary> userSummary = wxMpService.getDataCubeService()
                    .getUserSummary(beginDate, endDate);
            for (WxDataCubeUserSummary wxDataCubeUserSummary : userSummary) {
                newUser += wxDataCubeUserSummary.getNewUser();
                cancelUser += wxDataCubeUserSummary.getCancelUser();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        WeChatSummaryVo weChatSummaryVo = new WeChatSummaryVo();
        weChatSummaryVo.setName(weChatAccount.getName());
        weChatSummaryVo.setAppId(appId);
        weChatSummaryVo.setTotalUser(totalUser);
        weChatSummaryVo.setNewUser(newUser);
        weChatSummaryVo.setCancelUser(cancelUser);
        return weChatSummaryVo;
    }

}

