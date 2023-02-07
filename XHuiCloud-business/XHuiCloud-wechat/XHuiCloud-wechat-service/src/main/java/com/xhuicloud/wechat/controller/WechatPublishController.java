package com.xhuicloud.wechat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.common.core.constant.SecurityConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.AuditRecord;
import com.xhuicloud.wechat.config.WeChatMpCommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpFreePublishService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.freepublish.WxMpFreePublishList;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/11/26
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/publish/{appId}")
@Api(value = "publish", tags = "发布管理")
public class WechatPublishController {

    private final RedisTemplate redisTemplate;

    /**
     * 分页查询
     *
     * @param page 分页对象
     * @return Response
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public Response page(@PathVariable String appId, Page page) throws WxErrorException {
        WxMpFreePublishService wxMpFreePublishService = getWxMpFreePublishService(appId);
        WxMpFreePublishList result = wxMpFreePublishService.getPublicationRecords(
                Long.valueOf((page.getCurrent() - 1) * page.getSize()).intValue(), Long.valueOf(page.getSize()).intValue());
        page.setRecords(result.getItems());
        page.setTotal(result.getTotalCount());
        return Response.success(page);
    }


    @PostMapping
    @ApiOperation(value = "发布草稿", notes = "发布草稿")
    public Response publish(@PathVariable String appId, @RequestParam String mediaId) throws WxErrorException {
        Object codeObj = redisTemplate.opsForValue().get(CacheConstants.WECHAT_PUBLISH_CODE_KEY + mediaId);
        if (codeObj != null) {
            return Response.failed(Boolean.FALSE, "草稿发布过于频繁");
        }
        redisTemplate.opsForValue().set(
                CacheConstants.WECHAT_PUBLISH_CODE_KEY + mediaId, mediaId, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
        WxMpFreePublishService wxMpFreePublishService = getWxMpFreePublishService(appId);
        return Response.success(wxMpFreePublishService.submit(mediaId));
    }

    /**
     * 通过id删除
     *
     * @return Response
     */
    @AuditRecord("通过id删除")
    @DeleteMapping
//    @PreAuthorize("@authorize.hasPermission('sys_delete_draft')")
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    public Response delete(@PathVariable String appId, @RequestParam String articleId) throws WxErrorException {
        WxMpFreePublishService wxMpFreePublishService = getWxMpFreePublishService(appId);
        wxMpFreePublishService.deletePushAllArticle(articleId);
        return Response.success();
    }

    private WxMpFreePublishService getWxMpFreePublishService(String appId) {
        WxMpService wxMpService = WeChatMpCommonService.getWxMpService(appId);
        return wxMpService.getFreePublishService();
    }
}
