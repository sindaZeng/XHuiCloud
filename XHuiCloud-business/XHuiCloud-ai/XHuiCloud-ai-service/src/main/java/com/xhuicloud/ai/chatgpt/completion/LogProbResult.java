package com.xhuicloud.ai.chatgpt.completion;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Desc 记录不同令牌选项的概率 如果{@link CompletionRequest#logprobs}大于零返回
 * @Url https://beta.openai.com/docs/api-reference/create-completion
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class LogProbResult {


    /**
     * 每个符号输入的对数概率 {@link tokensS}
     */
    List<Double> tokenLogprobs;

    /**
     * 补全api选择的令牌
     */
    List<String> tokens;


    /**
     * 完成结果中每个索引的映射.
     * 该映射包含顶部 {@link CompletionRequest#logprobs} 令牌及其概率
     */
    List<Map<String, Double>> topLogprobs;

    /**
     * 对于每个选定的标记，从返回文本开始的字符偏移量。
     */
    List<Integer> textOffset;

}
