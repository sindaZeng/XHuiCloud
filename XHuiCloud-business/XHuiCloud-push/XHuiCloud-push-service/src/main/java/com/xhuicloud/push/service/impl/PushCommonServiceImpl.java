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
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.common.core.exception.ValidateException;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.mq.entity.push.PushMqEntity;
import com.xhuicloud.common.mq.service.CommonMqService;
import com.xhuicloud.push.common.*;
import com.xhuicloud.push.consumer.PushConsumer;
import com.xhuicloud.push.entity.PushGroup;
import com.xhuicloud.push.entity.PushTemplate;
import com.xhuicloud.push.enums.PushChannelEnum;
import com.xhuicloud.push.service.PushCommonService;
import com.xhuicloud.push.service.PushGroupService;
import com.xhuicloud.push.service.PushService;
import com.xhuicloud.push.service.PushTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PushCommonServiceImpl implements PushCommonService {

    private final CommonMqService commonMqService;

    private final PushGroupService pushGroupService;

    private final PushTemplateService pushTemplateService;

    private final Map<String, PushService> pushServiceMap;

    @Override
    public void toQueue(BasePush basePush) {
        if (basePush == null) {
            SysException.sysFail("推送载体不能为空~");
        }
        PushMqEntity pushMqEntity = new PushMqEntity();
        if (basePush instanceof PushSingle) {
            PushSingle pushSingle = (PushSingle) basePush;
            checkUserId(pushSingle.getUserId());
            checkParameter(pushSingle.getParams());
            if (pushGroupService.count(Wrappers.<PushGroup>lambdaQuery().eq(PushGroup::getTemplateCode, pushSingle.getTemplateCode())) <= 0) {
                SysException.sysFail("模板不存在~");
            }
            pushMqEntity.setCls(pushSingle.getClass());
        } else if (basePush instanceof PushMultiple) {
            PushMultiple pushMultiple = (PushMultiple) basePush;
            checkUserIds(pushMultiple.getUserIds());
            checkParameter(pushMultiple.getParams());
            if (pushGroupService.count(Wrappers.<PushGroup>lambdaQuery().eq(PushGroup::getTemplateCode, pushMultiple.getTemplateCode())) <= 0) {
                SysException.sysFail("模板不存在~");
            }
            pushMqEntity.setCls(pushMultiple.getClass());
        } else if (basePush instanceof PushMultiDiff) {
            PushMultiDiff pushMultiDiff = (PushMultiDiff) basePush;
            checkPushMultiDiff(pushMultiDiff);
            pushMqEntity.setCls(pushMultiDiff.getClass());
        }
        pushMqEntity.setTenantId(basePush.getTenantId());
        pushMqEntity.setJson(JSON.toJSONString(basePush));
        if (basePush.getSendTime() != null) {
            commonMqService.persistent(true).send(PushConsumer.QUEUE_NAME, pushMqEntity, basePush.getSendTime());
        } else {
            commonMqService.persistent(true).sendDirect(PushConsumer.QUEUE_NAME, pushMqEntity);
        }
    }

    @Override
    public Response single(PushSingle pushSingle) {
        PushGroup pushGroup = pushGroupService.getOne(Wrappers.<PushGroup>lambdaQuery().eq(PushGroup::getTemplateCode, pushSingle.getTemplateCode()));
        if (pushGroup == null) {
            SysException.sysFail("模板不存在~");
        }

        List<PushChannelEnum> pushChannelEnums = pushSingle.getPushChannelEnums();
        List<PushTemplate> pushTemplates;
        if (CollectionUtil.isNotEmpty(pushChannelEnums)) {
            pushTemplates = pushTemplateService.list(Wrappers.<PushTemplate>lambdaQuery()
                    .eq(PushTemplate::getGroupId, pushGroup.getId())
                    .in(PushTemplate::getChannel, pushChannelEnums.stream().map(PushChannelEnum::name)
                            .collect(Collectors.toList())));
        } else {
            pushTemplates = pushTemplateService.list(Wrappers.<PushTemplate>lambdaQuery()
                    .eq(PushTemplate::getGroupId, pushGroup.getId())
                    .in(PushTemplate::getChannel, pushChannelEnums.stream().map(PushChannelEnum::name)
                            .collect(Collectors.toList())));
        }
        if (CollectionUtil.isNotEmpty(pushTemplates)) {
            for (PushTemplate pushTemplate : pushTemplates) {
                PushService pushService = pushServiceMap.get(pushTemplate.getChannel());
                if (pushService != null) {
                    pushService.pushSingle(pushTemplate, pushSingle);
                }
            }
        }
        return Response.success();
    }

    @Override
    public Response multiple(PushMultiple pushMultiple) {
        return null;
    }

    @Override
    public Response multiDiff(PushMultiDiff pushMultiDiff) {
        return null;
    }

    void checkParameter(List<BasePushData.Parameter> params) {
        if (params == null || params.size() == 0) {
            throw ValidateException.validateFail("推送参数不能为空~");
        }
    }

    void checkUserId(Long userId) {
        if (null == userId) {
            throw ValidateException.validateFail("推送用户不能为空~");
        }
    }

    void checkUserIds(List<Long> userIds) {
        if (userIds == null || userIds.size() == 0) {
            throw ValidateException.validateFail("推送用户不能为空~");
        }
    }

    void checkPushMultiDiff(PushMultiDiff pushMultiDiff) {
        List<PushMultiDiff.Push> templates = pushMultiDiff.getTemplates();
        if (templates == null || templates.size() == 0) {
            throw ValidateException.validateFail("推送载体不能为空~");
        }
        List<String> templateCodes = Lists.newArrayList();
        for (PushMultiDiff.Push template : templates) {
            checkUserId(template.getUserId());
            checkParameter(template.getParams());
            templateCodes.add(template.getTemplateCode());
        }
        if (pushGroupService.count(Wrappers.<PushGroup>lambdaQuery().in(PushGroup::getTemplateCode, templateCodes)) != templateCodes.size()) {
            SysException.sysFail("部分模板不存在~");
        }
    }

}
