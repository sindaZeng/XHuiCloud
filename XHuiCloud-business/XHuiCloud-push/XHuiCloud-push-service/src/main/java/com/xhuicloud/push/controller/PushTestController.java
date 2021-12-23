package com.xhuicloud.push.controller;

import com.google.common.collect.Maps;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.push.common.BasePush;
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
