package com.xhuicloud.push.feign;

import com.xhuicloud.common.core.constant.ServiceNameConstants;
import com.xhuicloud.common.core.utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId = PushTestFeign.PUSHTESTFEIGN, value = ServiceNameConstants.XHUICLOUD_PUSH_SERVICE, path = "/test")
public interface PushTestFeign {

    String PUSHTESTFEIGN = "PushTestFeign";

    @GetMapping("/tm")
    Response tm();
}
