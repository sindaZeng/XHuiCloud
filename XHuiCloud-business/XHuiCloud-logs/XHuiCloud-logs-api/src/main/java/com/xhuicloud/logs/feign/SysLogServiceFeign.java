package com.xhuicloud.logs.feign;

import com.xhuicloud.common.core.constant.AuthorizationConstants;
import com.xhuicloud.common.core.constant.ServiceNameConstants;
import com.xhuicloud.logs.entity.SysLog;
import com.xhuicloud.common.core.utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = SysLogServiceFeign.SYSLOGSERVICEFEIGN, value = ServiceNameConstants.XHUICLOUD_LOGS_SERVICE, path = "/log")
public interface SysLogServiceFeign {

    String SYSLOGSERVICEFEIGN = "sysLogServiceFeign";

    @PostMapping("/save")
    Response save(@RequestBody SysLog sysLog, @RequestHeader(AuthorizationConstants.FROM) String from);

}
