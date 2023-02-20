package com.xhuicloud.ai.chatgpt.completion;

import lombok.Data;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class CompletionChoice {

    /**
     * 生成的文本。将包括提示，如果{@link CompletionRequest#echo } true
     */
    String text;

    /**
     * 该补全在返回列表中的索引。
     */
    Integer index;

    /**
     * 所选令牌和顶部{@link CompletionRequest#logprobs}令牌的日志概率
     */
    LogProbResult logprobs;

    /**
     * GPT-3停止生成的原因，例如“长度”。
     */
    String finish_reason;

}
