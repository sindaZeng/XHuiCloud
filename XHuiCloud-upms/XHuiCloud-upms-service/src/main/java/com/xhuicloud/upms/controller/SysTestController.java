package com.xhuicloud.upms.controller;

import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.logs.entity.SysLog;
import com.xhuicloud.logs.feign.SysLogServiceFeign;
import com.xhuicloud.push.feign.PushTestFeign;
import com.xhuicloud.upms.entity.SysRole;
import com.xhuicloud.upms.service.SysRoleService;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_INNER_YES;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
@Api(value = "test", tags = "upms测试模块")
public class SysTestController {

    private final SysRoleService sysRoleService;

    private final PushTestFeign pushTestFeign;

    private final SysLogServiceFeign sysLogServiceFeign;

    @GetMapping("/tm")
    @ApiOperation(value = "分布式事务测试", notes = "分布式事务测试")
    @GlobalTransactional
    public Response tm() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode("SINDA");
        sysRole.setRoleName("SINDA");
        sysRole.setRoleDesc("SINDA");
        sysRoleService.save(sysRole);
        pushTestFeign.tm();
        SysLog sysLog = new SysLog();
        sysLog.setRequestIp("TEST");
        sysLog.setType("TEST");
        sysLog.setUserName("TEST");
        sysLog.setDescription("TEST");
        sysLog.setClassPath("TEST");
        sysLog.setRequestMethod("TEST");
        sysLog.setRequestUri("TEST");
        sysLog.setHttpMethod("TEST");
        sysLog.setParams("TEST");
        sysLog.setResult("TEST");
        sysLog.setExDesc("TEST");
        sysLog.setExDetail("TEST");
        sysLogServiceFeign.save(sysLog, IS_COMMING_INNER_YES);
        throw new RuntimeException();
//        return Response.success();
    }
}
