package com.xhuicloud.push.feign;

import com.xhuicloud.common.core.constant.ServiceNameConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.push.common.PushMultiDiff;
import com.xhuicloud.push.common.PushMultiple;
import com.xhuicloud.push.common.PushSingle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId = PushCommonFeign.PUSHCOMMONFEIGN, value = ServiceNameConstants.XHUICLOUD_PUSH_SERVICE, path = "/send")
public interface PushCommonFeign {

    String PUSHCOMMONFEIGN = "PushCommonFeign";

    @PostMapping("/single")
    Response single(@RequestBody PushSingle pushSingle);

    @PostMapping("/multiple")
    Response multiple(@RequestBody PushMultiple pushMultiple);

    @PostMapping("/multiDiff")
    Response multiDiff(@RequestBody PushMultiDiff pushMultiDiff);

}
