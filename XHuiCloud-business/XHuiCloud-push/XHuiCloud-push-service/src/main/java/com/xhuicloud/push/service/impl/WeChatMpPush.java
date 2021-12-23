package com.xhuicloud.push.service.impl;

import com.xhuicloud.common.core.constant.SysParamConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.push.common.PushMultiple;
import com.xhuicloud.push.common.PushSingle;
import com.xhuicloud.push.service.PushService;
import com.xhuicloud.upms.entity.SysParam;
import com.xhuicloud.upms.feign.SysParamServiceFeign;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_INNER_YES;

@Component("WECHAT_MP")
@AllArgsConstructor
public class WeChatMpPush implements PushService {

    private final SysParamServiceFeign sysParamServiceFeign;

    @Override
    public boolean pushSingle(PushSingle pushSingle) {
        Response<SysParam> token = sysParamServiceFeign.get(SysParamConstants.WECHAT_MP_TOKEN, IS_COMMING_INNER_YES);
        String paramValue = token.getData().getParamValue();
        return false;
    }

    @Override
    public boolean pushMultiple(PushMultiple pushMultiple) {
        return false;
    }
}
