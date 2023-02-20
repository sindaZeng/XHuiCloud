package com.xhuicloud.ai.chatgpt.completion;

import com.xhuicloud.ai.chatgpt.model.Usage;
import lombok.Data;

import java.util.List;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class CompletionResult {

    /**
     * 分配给该补全的唯一id。
     */
    String id;

    /**
     * 返回的对象类型应该是"text_completion"
     */
    String object;

    /**
     * 秒为单位的创建时间。
     */
    long created;

    /**
     * 采用GPT-3模型。
     */
    String model;

    /**
     * 生成的完成列表。
     */
    List<CompletionChoice> choices;

    /**
     * 此请求的API使用情况
     */
    Usage usage;

}
