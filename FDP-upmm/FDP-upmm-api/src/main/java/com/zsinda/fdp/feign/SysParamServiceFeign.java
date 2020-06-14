package com.zsinda.fdp.feign;

import com.zsinda.fdp.constant.ServiceNameConstants;
import com.zsinda.fdp.entity.SysParam;
import com.zsinda.fdp.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static com.zsinda.fdp.constant.AuthorizationConstants.FROM;

@FeignClient(contextId = SysParamServiceFeign.SYSPARAMSERVICEFEIGN, value = ServiceNameConstants.FDP_UPMM_BUSINESS)
public interface SysParamServiceFeign {

    String SYSPARAMSERVICEFEIGN = "sysParamServiceFeign";

    /**
     * 查询系统租户列表
     *
     * @return
     */
    @GetMapping("/param/get")
    R<SysParam> get(@RequestParam(value = "key") String key, @RequestHeader(FROM) String from);
}
