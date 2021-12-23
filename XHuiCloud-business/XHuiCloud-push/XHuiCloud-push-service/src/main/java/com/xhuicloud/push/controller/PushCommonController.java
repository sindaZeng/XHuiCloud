package com.xhuicloud.push.controller;

import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.common.security.annotation.NoAuth;
import com.xhuicloud.push.common.PushMultiDiff;
import com.xhuicloud.push.common.PushMultiple;
import com.xhuicloud.push.common.PushSingle;
import com.xhuicloud.push.service.PushCommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
@AllArgsConstructor
@Api(value = "send", tags = "公共推送")
public class PushCommonController {

    private final PushCommonService pushCommonService;

    @NoAuth
    @SysLog("推送单人单一内容")
    @PostMapping("/single")
    @ApiOperation(value = "推送单人单一内容", notes = "推送单人单一内容")
    public Response single(@RequestBody PushSingle pushSingle) {
        pushCommonService.toQueue(pushSingle);
        return Response.success();
    }

    @SysLog("推送多人单一内容")
    @PostMapping("/multiple")
    @ApiOperation(value = "推送多人单一内容", notes = "推送多人单一内容")
    public Response multiple(@RequestBody PushMultiple pushMultiple) {
        pushCommonService.toQueue(pushMultiple);
        return Response.success();
    }

    @SysLog("推送多人不同内容")
    @PostMapping("/multiDiff")
    @ApiOperation(value = "推送多人不同内容", notes = "推送多人不同内容")
    public Response multiDiff(@RequestBody PushMultiDiff pushMultiDiff) {
        pushCommonService.toQueue(pushMultiDiff);
        return Response.success();
    }

}
