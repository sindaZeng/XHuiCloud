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

package com.xhuicloud.push.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xhuicloud.common.core.constant.SysParamConstants;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.push.common.BasePushData;
import com.xhuicloud.push.common.PushMultiple;
import com.xhuicloud.push.common.PushSingle;
import com.xhuicloud.push.entity.PushTemplate;
import com.xhuicloud.push.enums.PushChannelEnum;
import com.xhuicloud.push.service.PushService;
import com.xhuicloud.upms.entity.SysParam;
import com.xhuicloud.upms.entity.SysUserSocial;
import com.xhuicloud.upms.feign.SysParamServiceFeign;
import com.xhuicloud.upms.feign.SysUserServiceFeign;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.IS_COMMING_INNER_YES;

@Component("WECHAT_MP")
@AllArgsConstructor
public class WeChatMpPush implements PushService {

    private final SysParamServiceFeign sysParamServiceFeign;

    private final SysUserServiceFeign sysUserServiceFeign;

    /**
     * 模板发送地址
     */
    private static final String TEMPLATE_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

    @Override
    public boolean pushSingle(PushTemplate pushTemplate, PushSingle pushSingle) {

        Response<SysParam> response = sysParamServiceFeign.get(SysParamConstants.WECHAT_MP_TOKEN, IS_COMMING_INNER_YES);
        String token = response.getData().getParamValue();

        String url = String.format(TEMPLATE_SEND, token);
        HttpResponse httpResponse = HttpUtil.createPost(url)
                .body(JSONUtil.toJsonStr(getMsgBody(pushTemplate, pushSingle)))
                .timeout(10 * 10000)
                .execute();
        String body = httpResponse.body();
        return false;
    }

    private Map<String, Object> getMsgBody(PushTemplate pushTemplate, PushSingle pushSingle) {
        Response<SysUserSocial> sysUserSocialResponse = sysUserServiceFeign.getUserSocial(pushSingle.getUserId(), PushChannelEnum.WECHAT_MP.name(), IS_COMMING_INNER_YES);

        Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("touser", sysUserSocialResponse.getData().getUserOpenid());
        templateParams.put("template_id", pushTemplate.getChannelId());
        List<BasePushData.Parameter> params = pushSingle.getParams();
        Map<String, Object> dataMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(params)) {
            Map<String, String> paramMap = new HashMap<>();
            params.stream().forEach(param -> {
                paramMap.put(param.getKey(), param.getValue());
            });

            String kv = pushTemplate.getKv();
            JSONObject jsonObject = JSONUtil.parseObj(kv);
            if (StrUtil.isNotEmpty(kv)) {
                if (jsonObject != null) {
                    for (Map.Entry<String, Object> templateKv : jsonObject.entrySet()) {
                        Map<String,String> valueMap = new HashMap<>();
                        valueMap.put("value", paramMap.get(templateKv.getValue()));
                        dataMap.put(templateKv.getKey(), valueMap);
                    }
                }
            }else {
                for (Map.Entry<String, String> templateKv : paramMap.entrySet()) {
                    Map<String,String> valueMap = new HashMap<>();
                    valueMap.put("value", paramMap.get(templateKv.getValue()));
                    dataMap.put(templateKv.getKey(), valueMap);
                }
            }
        }
        templateParams.put("data", dataMap);
        return templateParams;
    }

    @Override
    public boolean pushMultiple(PushTemplate pushTemplate, PushMultiple pushMultiple) {
        return false;
    }
}
