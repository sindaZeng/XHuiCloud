package com.xhuicloud.upms.feign;

import com.xhuicloud.common.core.constant.ServiceNameConstants;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.common.core.utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.FROM;

@FeignClient(contextId = SysUserServiceFeign.SYSUSERSERVICEFEIGN, value = ServiceNameConstants.XHUICLOUD_UPMS_SERVICE, path = "/user")
public interface SysUserServiceFeign {

    String SYSUSERSERVICEFEIGN = "sysUserServiceFeign";

    @GetMapping("/info/{userName}")
    Response<UserInfo> getSysUser(@PathVariable("userName") String userName, @RequestHeader(FROM) String from);

    @PostMapping
    Response user(@RequestHeader(FROM) String from);

}
