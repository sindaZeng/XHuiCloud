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

package com.xhuicloud.push.controller;

import com.xhuicloud.common.authorization.resource.annotation.Anonymous;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.SysLog;
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

    @Anonymous
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
