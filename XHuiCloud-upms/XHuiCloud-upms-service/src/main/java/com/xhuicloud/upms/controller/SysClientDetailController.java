package com.xhuicloud.upms.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.security.annotation.NoAuth;
import com.xhuicloud.upms.entity.SysClientDetails;
import com.xhuicloud.upms.service.SysClientDetailsService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
@Api(value = "client", tags = "客户端管理")
public class SysClientDetailController {

    private final SysClientDetailsService sysClientDetailsService;

    @NoAuth(false)
    @GetMapping("/{clientId}")
    public Response getById(@PathVariable(value = "clientId") String clientId) {
        return Response.success(sysClientDetailsService.getOne(
                Wrappers.<SysClientDetails>lambdaQuery().eq(SysClientDetails::getClientId, clientId)));
    }
}
