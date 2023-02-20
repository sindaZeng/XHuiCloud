package com.xhuicloud.ai.chatgpt.model;

import lombok.Data;

/**
 * @Desc 请求使用的OpenAI资源
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class Usage {

    /**
     * 使用的提示令牌的数量。
     */
    long promptTokens;

    /**
     * 所使用的完成令牌的数量。
     */
    long completionTokens;

    /**
     * 使用的令牌总数
     */
    long totalTokens;
}
