package com.zsinda.fdp.feign;

import com.zsinda.fdp.constant.AuthorizationConstants;
import com.zsinda.fdp.constant.ServiceNameConstants;
import com.zsinda.fdp.entity.SysLog;
import com.zsinda.fdp.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = SysLogServiceFeign.SYSLOGSERVICEFEIGN, value = ServiceNameConstants.FDP_LOGS_BUSINESS)
public interface SysLogServiceFeign {

    String SYSLOGSERVICEFEIGN = "sysLogServiceFeign";

    @PostMapping("/log/save")
    R save(@RequestBody SysLog sysLog,@RequestHeader(AuthorizationConstants.FROM) String from);

}
