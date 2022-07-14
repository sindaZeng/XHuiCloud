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

package com.xhuicloud.upms.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xhuicloud.common.core.constant.SecurityConstants;
import com.xhuicloud.common.core.constant.SysParamConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.authorization.resource.annotation.Anonymous;
import com.xhuicloud.upms.entity.SysParam;
import com.xhuicloud.upms.entity.SysSocial;
import com.xhuicloud.upms.init.WeChatMpInit;
import com.xhuicloud.upms.service.SysParamService;
import com.xhuicloud.upms.service.SysSocialService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@Anonymous(value = false)
@RequestMapping("/wechat-mp/{appId}")
@RequiredArgsConstructor
@Api(value = "wechat-mp", tags = "微信公众号管理模块")
public class SysWechatMpController {

    /**
     * 临时二维码
     */
    private static String QR_STR_SCENE = "QR_STR_SCENE";

    /**
     * 永久二维码
     */
    private static String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";

    /**
     * 永久二维码
     */
    private static String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";


    private static String create_ticket_path = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";

    private static Integer expire_seconds = 30;

    private final SysSocialService sysSocialService;

    private final RedisTemplate redisTemplate;

    public SysSocial getSysSocial(String appId) {
        return sysSocialService.getOne(Wrappers.<SysSocial>lambdaQuery().eq(SysSocial::getAppId, appId));
    }

    @GetMapping("/login-qrcode")
    public Response loginQrcode(@PathVariable("appId") String appId) {
        String sceneStr = RandomUtil.randomString(30);
        SysSocial sysSocial = getSysSocial(appId);
        Map<String, String> intMap = new HashMap<>();
        intMap.put("scene_str", sceneStr);
        Map<String, Map<String, String>> mapMap = new HashMap<>();
        mapMap.put("scene", intMap);

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("expire_seconds", expire_seconds);
        paramsMap.put("action_name", QR_STR_SCENE);
        paramsMap.put("action_info", mapMap);

        String post = HttpUtil.post(String.format(create_ticket_path, sysSocial.getAppAccessToken()), JSONUtil.toJsonStr(paramsMap));
        String ticket = JSONUtil.parseObj(post).get("ticket").toString();
        redisTemplate.opsForValue().set(
                SecurityConstants.WECHAT_MP_SCAN + ticket
                , sceneStr, expire_seconds, TimeUnit.SECONDS);
        return Response.success(ticket);
    }


    @GetMapping("/scan-success")
    public Response scanSuccess(@PathVariable("appId") String appId, @RequestParam String ticket) {
        Object o = redisTemplate.opsForValue().get(
                SecurityConstants.WECHAT_MP_SCAN_SUCCESS + ticket);
        return Response.success(o != null);
    }

    @GetMapping
    public String wechatAuth(@PathVariable("appId") String appId,
                             @RequestParam(name = "signature", required = false) String signature,
                             @RequestParam(name = "timestamp", required = false) String timestamp,
                             @RequestParam(name = "nonce", required = false) String nonce,
                             @RequestParam(name = "echostr", required = false) String echostr) {
        log.info("微信公众平台认：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);

        if (StrUtil.isAllBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("认证参数不合法");
        }

        final WxMpService wxService = WeChatMpInit.getWxMpServiceMap().get(appId);

        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "错误的请求!";
    }

    @PostMapping
    public String post(@PathVariable("appId") String appId,
                       @RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {

        log.info("微信事件推送请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[{}] ",
                openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

        final WxMpService wxService = WeChatMpInit.getWxMpServiceMap().get(appId);
        final WxMpMessageRouter router = WeChatMpInit.getRouters().get(appId);

        if (!wxService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("参数不合法！");
        }
        if ("raw".equals(encType)) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = router.route(inMessage);
            if (outMessage != null) {
                return outMessage.toXml();
            }
        }

        if ("aes".equalsIgnoreCase(encType)) {
            WxMpXmlMessage inMessage = WxMpXmlMessage
                    .fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(),
                            timestamp, nonce, msgSignature);

            log.info("解密后：{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = router.route(inMessage);
            if (outMessage != null) {
                String out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
                log.info("返回信息:{}", out);
                return out;
            }
        }
        return "错误的请求!";
    }

}
