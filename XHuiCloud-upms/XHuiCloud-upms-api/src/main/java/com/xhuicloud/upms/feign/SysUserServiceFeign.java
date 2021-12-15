package com.xhuicloud.upms.feign;

import com.xhuicloud.common.core.constant.ServiceNameConstants;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.common.core.utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.FROM;

@FeignClient(contextId = SysUserServiceFeign.SYSUSERSERVICEFEIGN, value = ServiceNameConstants.XHUICLOUD_UPMS_SERVICE)
public interface SysUserServiceFeign {

    String SYSUSERSERVICEFEIGN = "sysUserServiceFeign";

    @GetMapping("/user/info/{userName}")
    Response<UserInfo> getSysUser(@PathVariable("userName") String userName, @RequestHeader(FROM) String from);

    @PostMapping("/user")
    Response user(@RequestHeader(FROM) String from);

}
