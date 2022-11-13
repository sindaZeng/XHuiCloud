package com.xhuicloud.wechat.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.authorization.resource.annotation.Anonymous;
import com.xhuicloud.common.core.constant.SecurityConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.wechat.config.WeChatAppIdContextHolder;
import com.xhuicloud.wechat.config.WeChatMpCommonService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @Desc 微信公众号入口
 * @Author Sinda
 * @Date 2022/11/7
 */
@Slf4j
@RestController
@Anonymous(value = false)
@RequestMapping("/mp/{appId}")
@RequiredArgsConstructor
@Api(value = "mp", tags = "微信公众号入口")
public class WechatMpController {

    private static Integer expire_seconds = 30;

    private final RedisTemplate redisTemplate;

    @SneakyThrows
    @GetMapping("/login-qrcode")
    public Response<String> loginQrcode(@PathVariable("appId") String appId) {
        String sceneStr = RandomUtil.randomString(30);
        WxMpService wxMpService = WeChatMpCommonService.getWxMpService(appId);
        WxMpQrcodeService qrcodeService = wxMpService.getQrcodeService();
        WxMpQrCodeTicket wxMpQrCodeTicket = qrcodeService.qrCodeCreateTmpTicket(sceneStr, expire_seconds);
        String ticket = wxMpQrCodeTicket.getTicket();
        redisTemplate.opsForValue().set(
                SecurityConstants.WECHAT_MP_SCAN + ticket
                , sceneStr, expire_seconds, TimeUnit.SECONDS);
        return Response.success(ticket);
    }


    @GetMapping("/scan-success")
    public Response<Boolean> scanSuccess(@PathVariable("appId") String appId, @RequestParam String ticket) {
        return Response.success(redisTemplate.hasKey(
                SecurityConstants.WECHAT_MP_SCAN_SUCCESS + ticket));
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
        final WxMpService wxService = WeChatMpCommonService.getWxMpServiceMap().get(appId);
        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "错误的请求!";
    }

    /**
     * 微信事件入口
     *
     * @param appId
     * @param requestBody
     * @param signature
     * @param timestamp
     * @param nonce
     * @param openid
     * @param encType
     * @param msgSignature
     * @return
     */
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

        final WxMpService wxService = WeChatMpCommonService.getWxMpServiceMap().get(appId);
        final WxMpMessageRouter router = WeChatMpCommonService.getRoutersMap().get(appId);

        if (!wxService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("参数不合法！");
        }
        WeChatAppIdContextHolder.setAppId(appId);
        if ("raw".equals(encType)) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = router.route(inMessage);
            if (outMessage != null) {
                WeChatAppIdContextHolder.remove();
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
                WeChatAppIdContextHolder.remove();
                return out;
            }
        }
        WeChatAppIdContextHolder.remove();
        return "错误的请求!";
    }

}
