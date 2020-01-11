package com.zsinda.fdp.feign;

import com.zsinda.fdp.dto.UserInfo;
import com.zsinda.fdp.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.zsinda.fdp.content.SecurityContent.FROM;

@FeignClient(contextId = "sysUserServiceFeign", value = "FDP-upmm-business")
public interface SysUserServiceFeign {

    @GetMapping("/user/getSysUser/{userName}")
    R<UserInfo> getSysUser(@PathVariable("userName") String userName, @RequestHeader(FROM) String from);

}
