package com.xhuicloud.ai.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/chat")
@Api(value = "chat", tags = "聊天")
public class ChatController {
}
