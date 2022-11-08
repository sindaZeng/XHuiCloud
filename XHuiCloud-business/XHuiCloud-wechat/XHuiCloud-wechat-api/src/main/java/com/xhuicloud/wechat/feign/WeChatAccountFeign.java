package com.xhuicloud.wechat.feign;

import com.xhuicloud.common.core.constant.ServiceNameConstants;
import com.xhuicloud.common.core.utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.FROM;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/11/7
 */
@FeignClient(contextId = WeChatAccountFeign.WECHATACCOUNTFEIGN, value = ServiceNameConstants.XHUICLOUD_WECHAT_SERVICE, path = "/account")
public interface WeChatAccountFeign {

    String WECHATACCOUNTFEIGN = "weChatAccountFeign";

    @PutMapping("/token")
    Response updateWechatToken(@RequestHeader(FROM) String from);

}
