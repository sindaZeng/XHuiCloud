package com.xhuicloud.job.xxlJob;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xhuicloud.common.core.constant.SysParamConstants;
import com.xhuicloud.common.core.constant.ThirdLoginUrlConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.upms.entity.SysParam;
import com.xhuicloud.upms.feign.SysParamServiceFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_INNER_YES;
import static com.xxl.job.core.biz.model.ReturnT.SUCCESS;

@Slf4j
@Component
@AllArgsConstructor
public class WeChatAccessTokenJob {

    private final SysParamServiceFeign sysParamServiceFeign;

    /**
     * 获取微信公众号token
     *
     * @return
     */
    @XxlJob("weChatAccessTokenJob")
    public ReturnT<String> weChatAccessTokenJob() {
        Response<SysParam> appId = sysParamServiceFeign.get(SysParamConstants.WECHAT_MP_APPID, IS_COMMING_INNER_YES);
        Response<SysParam> secret = sysParamServiceFeign.get(SysParamConstants.WECHAT_MP_SECRET, IS_COMMING_INNER_YES);
        if (appId.isSuccess() && secret.isSuccess()) {
            String url = String.format(ThirdLoginUrlConstants.MINI_WECHAT_ACCESS_TOKEN, appId.getData().getParamValue(), secret.getData().getParamValue());
            String result = HttpUtil.get(url);
            JSONObject response = JSONUtil.parseObj(result);
            String access_token = response.getStr("access_token");
            sysParamServiceFeign.updateValue(SysParam.builder().paramKey(SysParamConstants.WECHAT_MP_TOKEN).paramValue(access_token).build(), IS_COMMING_INNER_YES);
        }
        return SUCCESS;
    }
}
