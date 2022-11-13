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

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhuicloud.common.core.enums.base.BooleanEnum;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.wechat.entity.WeChatAccount;
import com.xhuicloud.wechat.entity.WechatMenus;
import com.xhuicloud.wechat.config.WeChatMpCommonService;
import com.xhuicloud.wechat.mapper.WechatMenusMapper;
import com.xhuicloud.wechat.service.WeChatAccountService;
import com.xhuicloud.wechat.service.WechatMenusService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: wechat
 * @description: 公众号菜单
 * @author: Sinda
 * @create: 2022-11-12 10:24:51
 */
@Service
@AllArgsConstructor
public class WechatMenusServiceImpl extends ServiceImpl<WechatMenusMapper, WechatMenus> implements WechatMenusService {

    private final WeChatAccountService weChatAccountService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response release(String appId, JSONObject menu) {
        WxMpService wxMpService = WeChatMpCommonService.getWxMpService(appId);
        baseMapper.delete(Wrappers.<WechatMenus>lambdaQuery().eq(WechatMenus::getAppId, appId));
        WeChatAccount weChatAccount = weChatAccountService.getOne(Wrappers.<WeChatAccount>lambdaQuery().eq(WeChatAccount::getAppId, appId));
        WechatMenus wechatMenus = new WechatMenus();
        wechatMenus.setAppId(appId);
        wechatMenus.setAccountId(weChatAccount.getId());
        wechatMenus.setMenu(menu.toStringPretty());
        wechatMenus.setIsRelease(BooleanEnum.YES.getCode());
        save(wechatMenus);
        try {
            WxMpMenuService menuService = wxMpService.getMenuService();
            WxMenu wxMenu = JSONUtil.toBean(wechatMenus.getMenu(), WxMenu.class);
            menuService.menuCreate(wxMenu);
        } catch (WxErrorException e) {
            wechatMenus.setIsRelease(BooleanEnum.NO.getCode());
            updateById(wechatMenus);
            return Response.failed(String.format("发布失败,已保存为草稿。失败原因:%s", e.getError().getErrorMsg()));
        }
        return Response.success();
    }
}

