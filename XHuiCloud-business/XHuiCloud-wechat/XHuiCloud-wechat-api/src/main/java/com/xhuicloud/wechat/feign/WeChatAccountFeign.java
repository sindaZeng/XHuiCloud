package com.xhuicloud.wechat.feign;

import com.xhuicloud.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/11/7
 */
@FeignClient(contextId = WeChatAccountFeign.WECHATACCOUNTFEIGN, value = ServiceNameConstants.XHUICLOUD_WECHAT_SERVICE, path = "/account")
public interface WeChatAccountFeign {

    String WECHATACCOUNTFEIGN = "weChatAccountFeign";

}
