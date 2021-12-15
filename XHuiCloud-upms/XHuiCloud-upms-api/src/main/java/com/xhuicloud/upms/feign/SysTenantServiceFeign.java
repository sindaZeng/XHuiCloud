package com.xhuicloud.upms.feign;

import com.xhuicloud.common.core.constant.ServiceNameConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.upms.entity.SysTenant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.FROM;

@FeignClient(contextId = SysTenantServiceFeign.SYSTENANTSERVICEFEIGN, value = ServiceNameConstants.XHUICLOUD_UPMS_SERVICE)
public interface SysTenantServiceFeign {

    String SYSTENANTSERVICEFEIGN = "sysTenantServiceFeign";

    /**
     * 查询系统租户列表
     *
     * @return
     */
    @GetMapping("/tenant/list")
    Response<List<SysTenant>> list(@RequestHeader(FROM) String from);

}
