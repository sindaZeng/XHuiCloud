package com.xhuicloud.upms.feign;

import com.xhuicloud.common.core.constant.ServiceNameConstants;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.common.core.utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.FROM;

/**
 * @program: XHuiCloud
 * @description: SysSocialServiceFeign
 * @author: Sinda
 * @create: 2020-06-17 16:31
 */
@FeignClient(contextId = SysSocialServiceFeign.SYSSOCIALSERVICEFEIGN, value = ServiceNameConstants.XHUICLOUD_UPMS_SERVICE, path = "/social")
public interface SysSocialServiceFeign {

    String SYSSOCIALSERVICEFEIGN = "sysSocialServiceFeign";

    @GetMapping("/{auth_code}")
    Response<UserInfo> getSysUser(@PathVariable(value = "auth_code") String auth_code, @RequestHeader(FROM) String from);

}
