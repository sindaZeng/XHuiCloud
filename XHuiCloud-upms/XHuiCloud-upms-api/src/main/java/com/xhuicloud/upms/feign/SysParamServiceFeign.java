package com.xhuicloud.upms.feign;

import com.xhuicloud.common.core.constant.ServiceNameConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.upms.entity.SysParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.FROM;

@FeignClient(contextId = SysParamServiceFeign.SYSPARAMSERVICEFEIGN, value = ServiceNameConstants.XHUICLOUD_UPMS_SERVICE)
public interface SysParamServiceFeign {

    String SYSPARAMSERVICEFEIGN = "sysParamServiceFeign";

    /**
     * 查询系统租户列表
     *
     * @return
     */
    @GetMapping("/param/get")
    Response<SysParam> get(@RequestParam(value = "key") String key, @RequestHeader(FROM) String from);

    @PutMapping("/param/updateValue")
    Response updateValue(@RequestBody SysParam sysParam, @RequestHeader(FROM) String from);

}
