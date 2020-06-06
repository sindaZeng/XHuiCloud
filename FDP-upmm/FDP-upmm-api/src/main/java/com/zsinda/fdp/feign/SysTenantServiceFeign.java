package com.zsinda.fdp.feign;

import com.zsinda.fdp.constant.ServiceNameConstants;
import com.zsinda.fdp.entity.SysTenant;
import com.zsinda.fdp.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.zsinda.fdp.constant.AuthorizationConstants.FROM;

@FeignClient(contextId = SysTenantServiceFeign.SYSTENANTSERVICEFEIGN, value = ServiceNameConstants.FDP_UPMM_BUSINESS)
public interface SysTenantServiceFeign {

    String SYSTENANTSERVICEFEIGN = "sysTenantServiceFeign";

    /**
     * 查询系统租户列表
     *
     * @return
     */
    @GetMapping("/tenant/list")
    R<List<SysTenant>> list(@RequestHeader(FROM) String from);

    /**
     * 查询系统租户
     *
     * @return
     */
    @GetMapping("/tenant/get")
    R get(@RequestParam(value = "tenantId") String tenantId, @RequestHeader(FROM) String from);
}
