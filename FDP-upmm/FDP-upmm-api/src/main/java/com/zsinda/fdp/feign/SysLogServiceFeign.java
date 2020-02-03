package com.zsinda.fdp.feign;

import com.zsinda.fdp.constant.ServiceNameConstants;
import com.zsinda.fdp.entity.SysLog;
import com.zsinda.fdp.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.zsinda.fdp.constant.AuthorizationConstants.FROM;

@FeignClient(contextId = "sysLogServiceFeign", value = ServiceNameConstants.FDP_UPMM_BUSINESS)
public interface SysLogServiceFeign {

    @PostMapping("/log/save")
    R save(@RequestBody SysLog sysLog,@RequestHeader(FROM) String from);

}
