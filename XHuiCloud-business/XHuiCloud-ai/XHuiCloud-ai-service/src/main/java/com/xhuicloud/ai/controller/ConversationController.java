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

package com.xhuicloud.ai.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.xhuicloud.ai.entity.Conversation;
import com.xhuicloud.ai.service.ConversationService;
import com.xhuicloud.ai.vo.MessagesVo;
import com.xhuicloud.common.authorization.resource.userdetails.XHuiUser;
import com.xhuicloud.common.authorization.resource.utils.SecurityHolder;
import com.xhuicloud.common.core.utils.DateUtils;
import com.xhuicloud.common.core.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: ai
 * @description:
 * @author: Sinda
 * @create: 2023-02-11 21:45:13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/conversation")
@Api(value = "conversation", tags = "管理")
public class ConversationController {

    private final ConversationService conversationService;

    @GetMapping
    @ApiOperation(value = "聊天记录查询(只查询当天)", notes = "聊天记录查询")
    public Response<List<MessagesVo>> get() {
        XHuiUser user = SecurityHolder.getUser();
        String today = DateUtils.getDate();
        List<Conversation> list = conversationService.list(Wrappers.<Conversation>lambdaQuery()
                .eq(Conversation::getUserId, user.getId())
                .between(Conversation::getCreateTime, today + " 00:00:00", today + " 23:59:59")
                .orderByDesc(Conversation::getCreateTime));
        List<MessagesVo> messagesVos = Lists.newLinkedList();
        for (Conversation conversation : list) {
            MessagesVo answer = new MessagesVo();
            answer.setMessageId(conversation.getMessageId());
            answer.setText(conversation.getAnswer());
            answer.setUserId("system");
            messagesVos.add(answer);

            MessagesVo question = new MessagesVo();
            question.setText(conversation.getQuestion());
            question.setUserId(user.getId() + "");
            messagesVos.add(question);
        }
        return Response.success(messagesVos);
    }


}
