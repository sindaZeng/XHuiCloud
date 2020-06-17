package com.zsinda.fdp.feign;

import com.zsinda.fdp.constant.ServiceNameConstants;
import com.zsinda.fdp.dto.UserInfo;
import com.zsinda.fdp.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.zsinda.fdp.constant.AuthorizationConstants.FROM;

@FeignClient(contextId = SysUserServiceFeign.SYSUSERSERVICEFEIGN, value = ServiceNameConstants.FDP_UPMM_BUSINESS)
public interface SysUserServiceFeign {

    String SYSUSERSERVICEFEIGN = "sysUserServiceFeign";

    @GetMapping("/user/{userName}")
    R<UserInfo> getSysUser(@PathVariable("userName") String userName, @RequestHeader(FROM) String from);

    @PostMapping("/user")
    R user(@RequestHeader(FROM) String from);

}
