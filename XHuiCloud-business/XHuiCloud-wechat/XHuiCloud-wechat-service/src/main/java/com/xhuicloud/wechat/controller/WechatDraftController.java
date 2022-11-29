package com.xhuicloud.wechat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.wechat.config.WeChatMpCommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpDraftService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.draft.WxMpDraftList;
import org.springframework.web.bind.annotation.*;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/11/26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/draft/{appId}")
@Api(value = "draft", tags = "草稿箱管理")
public class WechatDraftController {

    /**
     * 分页查询
     *
     * @param page 分页对象
     * @return Response
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public Response page(@PathVariable String appId, Page page) throws WxErrorException {
        WxMpDraftService draftService = getDraftService(appId);
        WxMpDraftList result = draftService.listDraft(
                Long.valueOf((page.getCurrent() - 1) * page.getSize()).intValue(), Long.valueOf(page.getSize()).intValue());
        page.setRecords(result.getItems());
        page.setTotal(result.getTotalCount());
        return Response.success(page);
    }

    /**
     * 通过id删除
     *
     * @return Response
     */
    @SysLog("通过id删除")
    @DeleteMapping
//    @PreAuthorize("@authorize.hasPermission('sys_delete_draft')")
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    public Response delete(@PathVariable String appId, @RequestParam String mediaId) throws WxErrorException {
        WxMpDraftService draftService = getDraftService(appId);
        draftService.delDraft(mediaId);
        return Response.success();
    }

    private WxMpDraftService getDraftService(String appId) {
        WxMpService wxMpService = WeChatMpCommonService.getWxMpService(appId);
        return wxMpService.getDraftService();
    }
}
