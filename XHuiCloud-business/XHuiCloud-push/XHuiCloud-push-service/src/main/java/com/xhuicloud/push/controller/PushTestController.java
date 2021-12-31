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

import com.google.common.collect.Maps;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.push.common.PushSingle;
import com.xhuicloud.push.entity.PushTemplate;
import com.xhuicloud.push.enums.WeChatMpMessage;
import com.xhuicloud.push.service.PushTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
@Api(value = "test", tags = "push测试模块")
public class PushTestController {

    private final PushTemplateService pushTemplateService;

    @GetMapping("/tm")
    @ApiOperation(value = "分布式事务测试", notes = "分布式事务测试")
    public Response tm() {
        PushTemplate pushTemplate = new PushTemplate();
        pushTemplate.setRemark("");
        pushTemplate.setChannel("TEST");
        pushTemplate.setContent("TEST");
        pushTemplate.setStatus(false);
        pushTemplate.setKv("TEST");
        pushTemplateService.save(pushTemplate);
        return Response.success();
    }

    public static void main(String[] args) {
        Map<String, String> params = Maps.newHashMap();
        params.put("first.DATA","1");
        params.put("keyword1.DATA","2");
        params.put("keyword2.DATA","3");
        params.put("keyword3.DATA","4");
        params.put("keyword4.DATA","5");
        params.put("remark.DATA","6");
        PushSingle pushSingle = WeChatMpMessage.LOGIN_SUCCESS.getPushSingle(params);
        System.out.println();
    }

}
