package com.xhuicloud.wechat.controller;

import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.wechat.service.WeChatAccountService;
import com.xhuicloud.wechat.vo.WeChatSummaryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/11/9
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user/{appId}")
@Api(value = "user", tags = "公众号用户管理")
public class WeChatUserController {

    private final WeChatAccountService weChatAccountService;

    /**
     * 获取用户增减数据
     *
     * @return Response
     */
    @GetMapping("/getUserSummary")
    @ApiOperation(value = "获取用户增减数据", notes = "获取用户增减数据")
    public Response<WeChatSummaryVo> getUserSummary(@PathVariable String appId) {
        return Response.success(weChatAccountService.getUserSummary(appId));
    }
}

