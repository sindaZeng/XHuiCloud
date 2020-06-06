package com.zsinda.fdp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsinda.fdp.annotation.SysLog;
import com.zsinda.fdp.entity.PayChannel;
import com.zsinda.fdp.service.PayChannelService;
import com.zsinda.fdp.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @program: FDPlatform
 * @description: PayChannelController
 * @author: Sinda
 * @create: 2020-06-05 14:40
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/channel")
@Api(value = "channel", tags = "商户渠道管理")
public class PayChannelController {

    private final PayChannelService payChannelService;

    /**
     * 分页查询商户渠道列表
     *
     * @return
     */
    @GetMapping("/page")
    public R page(Page page) {
        return R.ok(payChannelService.page(page));
    }

    /**
     * 添加商户渠道
     *
     * @return
     */
    @SysLog("添加商户渠道")
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_channel')")
    public R save(@RequestBody PayChannel payChannel) {
        return R.ok(payChannelService.save(payChannel));
    }

    /**
     * 编辑商户渠道
     *
     * @param payChannel
     * @return
     */
    @SysLog("编辑商户渠道")
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_user')")
    public R update(@RequestBody PayChannel payChannel) {
        return R.ok(payChannelService.updateById(payChannel));
    }
}
