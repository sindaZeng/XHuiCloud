package com.xhuicloud.upms.feign;

import com.xhuicloud.common.core.constant.ServiceNameConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.upms.entity.SysClientDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.FROM;

@FeignClient(contextId = SysClientDetailFeign.SYSCLIENTDETAILFEIGN, value = ServiceNameConstants.XHUICLOUD_UPMS_SERVICE)
public interface SysClientDetailFeign {

    String SYSCLIENTDETAILFEIGN = "sysClientDetailFeign";

    @GetMapping("/client/{clientId}")
    Response<SysClientDetails> getById(@PathVariable(value = "clientId") String clientId, @RequestHeader(FROM) String from);

}
