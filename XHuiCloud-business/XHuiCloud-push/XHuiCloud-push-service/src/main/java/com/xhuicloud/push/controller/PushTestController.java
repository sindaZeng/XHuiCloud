package com.xhuicloud.push.controller;
import java.util.Date;

import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.push.entity.PushTemplate;
import com.xhuicloud.push.service.PushTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        pushTemplate.setTemplateId("TEST");
        pushTemplate.setTitle("TEST");
        pushTemplateService.save(pushTemplate);
        return Response.success();
    }
}
