package com.xhuicloud.wechat.controller;

import cn.hutool.core.collection.CollectionUtil;
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
import me.chanjar.weixin.mp.bean.draft.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @ApiOperation(value = "获取图文", notes = "获取图文")
    public Response<WxMpDraftInfo> get(@PathVariable String appId, @RequestParam String mediaId) throws WxErrorException {
        WxMpDraftService draftService = getDraftService(appId);
        return Response.success(draftService.getDraft(mediaId));
    }

    @PostMapping
    @ApiOperation(value = "保存草稿图文素材列表", notes = "保存草稿图文素材列表")
    public Response save(@PathVariable String appId, @RequestBody WxMpAddDraft wxMpAddDraft) throws WxErrorException {
        WxMpDraftService draftService = getDraftService(appId);
        return Response.success(draftService.addDraft(wxMpAddDraft));
    }

    @PostMapping("/save/{mediaId}")
    @ApiOperation(value = "单图文消息新增图文消息(单图文消息转多图文消息)", notes = "保存草稿图文素材列表")
    public Response save(@PathVariable String appId, @PathVariable String mediaId, @RequestBody WxMpAddDraft wxMpAddDraft) throws WxErrorException {
        // 假设原本只有一条,新增图文后 > 1,则需要删除此图文后重新添加,否则会报错40114,微信公众号暂未解决
        WxMpDraftService draftService = getDraftService(appId);
        draftService.delDraft(mediaId);
        return Response.success(draftService.addDraft(wxMpAddDraft));
    }

    /**
     * 原图文数量没变才使用此接口
     * 主要作用:修改文章内容,修改文章顺序
     *
     * @param appId
     * @param mediaId
     * @param wxMpAddDraft
     * @return
     * @throws WxErrorException
     */
    @PutMapping("/{mediaId}")
    @ApiOperation(value = "修改草稿信息", notes = "修改草稿信息")
    public Response update(@PathVariable String appId, @PathVariable String mediaId, @RequestBody WxMpAddDraft wxMpAddDraft) throws WxErrorException {
        List<WxMpDraftArticles> articles = wxMpAddDraft.getArticles();
        if (CollectionUtil.isEmpty(articles)) {
            return Response.failed("文章不能为空!");
        }
        WxMpDraftService draftService = getDraftService(appId);
        for (int i = 0; i < articles.size(); i++) {
            WxMpUpdateDraft wxMpUpdateDraft = new WxMpUpdateDraft();
            wxMpUpdateDraft.setMediaId(mediaId);
            wxMpUpdateDraft.setIndex(i);
            wxMpUpdateDraft.setArticles(articles.get(i));
            draftService.updateDraft(wxMpUpdateDraft);
        }
        return Response.success();
    }

    /**
     * 通过id删除
     *
     * @return Response
     */
    @SysLog("通过id删除")
    @DeleteMapping
    @PreAuthorize("@authorize.hasPermission('sys_delete_draft')")
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
