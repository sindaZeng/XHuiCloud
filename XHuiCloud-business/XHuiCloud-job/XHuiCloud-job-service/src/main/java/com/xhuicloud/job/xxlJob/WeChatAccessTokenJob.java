/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

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

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_ANONYMOUS_YES;
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
        Response<SysParam> appId = sysParamServiceFeign.get(SysParamConstants.WECHAT_MP_APPID, IS_COMMING_ANONYMOUS_YES);
        Response<SysParam> secret = sysParamServiceFeign.get(SysParamConstants.WECHAT_MP_SECRET, IS_COMMING_ANONYMOUS_YES);
        if (appId.getData() != null && secret.getData() != null) {
            String url = String.format(ThirdLoginUrlConstants.MINI_WECHAT_ACCESS_TOKEN, appId.getData().getParamValue(), secret.getData().getParamValue());
            String result = HttpUtil.get(url);
            JSONObject response = JSONUtil.parseObj(result);
            String access_token = response.getStr("access_token");
            sysParamServiceFeign.updateValue(SysParam.builder().paramKey(SysParamConstants.WECHAT_MP_TOKEN).paramValue(access_token).build(), IS_COMMING_ANONYMOUS_YES);
        }
        return SUCCESS;
    }
}
