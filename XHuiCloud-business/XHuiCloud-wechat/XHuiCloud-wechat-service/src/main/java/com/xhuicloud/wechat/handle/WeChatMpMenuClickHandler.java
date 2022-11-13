package com.xhuicloud.wechat.handle;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xhuicloud.common.core.enums.base.BooleanEnum;
import com.xhuicloud.wechat.config.WeChatAppIdContextHolder;
import com.xhuicloud.wechat.entity.WechatMenus;
import com.xhuicloud.wechat.service.WechatMenusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.builder.outxml.TextBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Desc 菜单点击事件回调
 * @Author Sinda
 * @Date 2022/11/12
 */
@Slf4j
@Component
@AllArgsConstructor
public class WeChatMpMenuClickHandler implements WxMpMessageHandler {

    private final WechatMenusService wechatMenusService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context,
                                    WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        String appId = WeChatAppIdContextHolder.getAppId();
        WechatMenus wechatMenus = wechatMenusService.getOne(Wrappers.<WechatMenus>lambdaQuery()
                .eq(WechatMenus::getAppId, appId).eq(WechatMenus::getIsRelease, BooleanEnum.YES.getCode()));
        List<JSONObject> buttons = JSONUtil.parseObj(wechatMenus.getMenu()).getJSONArray("buttons")
                .toList(JSONObject.class);

        String eventKey = wxMessage.getEventKey();

        // 把所有按钮拍平
        List<JSONObject> buttonList = new ArrayList<>();
        for (JSONObject button : buttons) {
            JSONArray subButtons = button.getJSONArray("subButtonList");
            if (subButtons.isEmpty()) {
                buttonList.add(button);
                continue;
            }
            buttonList.addAll(subButtons.toList(JSONObject.class));
        }

        for (JSONObject button : buttonList) {
            if (!eventKey.equalsIgnoreCase(button.getStr("key"))) {
                continue;
            }

            String resType = button.getStr("resType");
            String resContent = button.getStr("resContent");
            WxMpXmlOutMessage wxMpXmlOutMessage = null;
            if (WxConsts.KefuMsgType.TEXT.equals(resType)) {
                wxMpXmlOutMessage = new TextBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                        .content(resContent).build();
            }
            return wxMpXmlOutMessage;
        }
        return null;
    }
}
