package com.xhuicloud.wechat.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xhuicloud.common.core.enums.base.BooleanEnum;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.wechat.entity.WechatMenus;
import com.xhuicloud.wechat.service.WechatMenusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/11/11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/menu/{appId}")
@Api(value = "menu", tags = "公众号菜单管理")
public class WeChatMenuController {

    private final WechatMenusService wechatMenusService;

    /**
     * 获取公众号菜单数据
     *
     * @return Response
     */
    @GetMapping
    @ApiOperation(value = "获取公众号菜单数据", notes = "获取公众号菜单数据")
    public Response menus(@PathVariable String appId) {
        WechatMenus wechatMenus = wechatMenusService.getOne(Wrappers.<WechatMenus>lambdaQuery().eq(WechatMenus::getAppId, appId).eq(WechatMenus::getIsRelease, BooleanEnum.YES.getCode()));
        if (wechatMenus == null) {
            return Response.success();
        }
        return Response.success(wechatMenus.getMenu());
    }

    /**
     * 保存并发布公众号菜单数据
     *
     * @return Response
     */
    @PostMapping("/release")
    @ApiOperation(value = "保存并发布公众号菜单数据", notes = "保存并发布公众号菜单数据")
    public Response release(@PathVariable String appId, @RequestBody JSONObject menu) {
        return wechatMenusService.release(appId, menu);
    }
}
