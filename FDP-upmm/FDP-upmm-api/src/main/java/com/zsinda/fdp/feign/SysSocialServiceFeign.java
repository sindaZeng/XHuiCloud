package com.zsinda.fdp.feign;

import com.zsinda.fdp.constant.ServiceNameConstants;
import com.zsinda.fdp.dto.UserInfo;
import com.zsinda.fdp.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.zsinda.fdp.constant.AuthorizationConstants.FROM;

/**
 * @program: FDPlatform
 * @description: SysSocialServiceFeign
 * @author: Sinda
 * @create: 2020-06-17 16:31
 */
@FeignClient(contextId = SysSocialServiceFeign.SYSSOCIALSERVICEFEIGN, value = ServiceNameConstants.FDP_UPMM_BUSINESS)
public interface SysSocialServiceFeign {

    String SYSSOCIALSERVICEFEIGN = "sysSocialServiceFeign";

    @GetMapping("/social/{auth_code}")
    R<UserInfo> getSysUser(@PathVariable(value = "auth_code") String auth_code, @RequestHeader(FROM) String from);

}
