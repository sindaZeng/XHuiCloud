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

package com.xhuicloud.wechat.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.wechat.config.WeChatMpCommonService;
import com.xhuicloud.wechat.entity.WeChatAccount;
import com.xhuicloud.wechat.service.WeChatAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @program: wechat
 * @description: 公众号账户
 * @author: Sinda
 * @create: 2022-11-04 17:05:04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/account")
@Api(value = "account", tags = "公众号账户管理")
public class WeChatAccountController {

    private final WeChatAccountService weChatAccountService;

    private final RedisTemplate redisTemplate;

    /**
     * 分页查询
     *
     * @param page          分页对象
     * @param weChatAccount 公众号账户
     * @return Response
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public Response page(Page page, WeChatAccount weChatAccount) {
        return Response.success(weChatAccountService.page(page, Wrappers.query(weChatAccount)));
    }

    /**
     * 查询全部
     *
     * @return Response
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询全部", notes = "查询全部")
    public Response list(@RequestParam(required = false) String name) {
        return Response.success(weChatAccountService.list(Wrappers.<WeChatAccount>lambdaQuery().like(StrUtil.isNotBlank(name), WeChatAccount::getName, name)));
    }


    /**
     * 通过id查询公众号账户
     *
     * @param id
     * @return Response
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询公众号账户", notes = "通过id查询公众号账户")
    public Response getById(@PathVariable Integer id) {
        return Response.success(weChatAccountService.getById(id));
    }

    /**
     * 查询公众号token
     *
     * @param appId
     * @return
     */
    @SneakyThrows
    @GetMapping("/{appId}/access_token")
    @ApiOperation(value = "通过appId查询access_token", notes = "通过appId查询access_token")
    public Response accessToken(@PathVariable String appId) {
        WxMpService wxMpService = WeChatMpCommonService.getWxMpService(appId);
        return Response.success(wxMpService.getAccessToken());
    }

    /**
     * 通过appId生成场景二维码
     *
     * @param appId
     * @return
     */
    @SneakyThrows
    @GetMapping("/{appId}/qrCode")
    @ApiOperation(value = "通过appId生成场景二维码", notes = "通过appId生成场景二维码")
    public Response qrCode(@PathVariable String appId, @RequestParam String sceneStr) {
        WxMpService wxMpService = WeChatMpCommonService.getWxMpService(appId);
        WxMpQrcodeService qrcodeService = wxMpService.getQrcodeService();
        WxMpQrCodeTicket wxMpQrCodeTicket = qrcodeService.qrCodeCreateTmpTicket(sceneStr, 24 * 60 * 60);
        return Response.success(qrcodeService.qrCodePictureUrl(wxMpQrCodeTicket.getTicket()));
    }

    /**
     * 删除调用次数
     *
     * @param appId
     * @return
     */
    @SneakyThrows
    @DeleteMapping("/{appId}/clear_quota")
    @ApiOperation(value = "通过appId删除调用次数", notes = "通过appId删除调用次数")
    public Response clearQuota(@PathVariable String appId) {
        WxMpService wxMpService = WeChatMpCommonService.getWxMpService(appId);
        wxMpService.clearQuota(appId);
        return Response.success();
    }

    /**
     * 新增公众号账户
     *
     * @param weChatAccount 公众号账户
     * @return Response
     */
    @SysLog("新增公众号账户")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_account')")
    @ApiOperation(value = "新增公众号账户", notes = "新增公众号账户")
    public Response save(@RequestBody WeChatAccount weChatAccount) {
        weChatAccountService.save(weChatAccount);
        redisTemplate.convertAndSend(CommonConstants.WECHAT_CLIENT_RELOAD, "重新加载公众号配置事件");
        return Response.success();
    }

    /**
     * 修改公众号账户
     *
     * @param weChatAccount 公众号账户
     * @return Response
     */
    @SysLog("编辑公众号账户")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_account')")
    @ApiOperation(value = "修改公众号账户", notes = "修改公众号账户")
    public Response update(@RequestBody WeChatAccount weChatAccount) {
        weChatAccountService.updateById(weChatAccount);
        redisTemplate.convertAndSend(CommonConstants.WECHAT_CLIENT_RELOAD, "重新加载公众号配置事件");
        return Response.success();
    }

    /**
     * 通过id删除公众号账户
     *
     * @param id
     * @return Response
     */
    @SysLog("通过id删除公众号账户")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_account')")
    @ApiOperation(value = "通过id删除公众号账户", notes = "通过id删除公众号账户")
    public Response delete(@PathVariable Integer id) {
        weChatAccountService.removeById(id);
        redisTemplate.convertAndSend(CommonConstants.WECHAT_CLIENT_RELOAD, "重新加载公众号配置事件");
        return Response.success();
    }

}
