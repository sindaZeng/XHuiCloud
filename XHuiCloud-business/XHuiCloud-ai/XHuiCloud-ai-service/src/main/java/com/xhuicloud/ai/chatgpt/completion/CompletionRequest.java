package com.xhuicloud.ai.chatgpt.completion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Desc 请求OpenAi为提示生成预测
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompletionRequest {
    /**
     * 要使用的模型名称。
     * 如果指定一个微调模型或使用新的 v1/completions 则必填
     */
    String model;

    /**
     * 可选的完成提示符
     */
    String prompt;

    /**
     * 生成的最大令牌数量。
     * 请求在提示和完成之间最多可以使用2048个令牌共享。
     * (对于普通英文文本，一个标记大约是4个字符)
     */
    Integer maxTokens;

    /**
     * 默认值为 1
     * 使用什么采样温度，介于 0 和 2 之间。较高的值（如 0.8）将使输出更加随机，而较低的值（如 0.2）将使其更加集中和确定。
     * 我们通常建议更改此或top_p，但不能同时更改两者。
     */
    Double temperature;

    /**
     * 使用温度采样的替代方法称为核心采样，其中模型考虑具有top_p概率质量的令牌的结果。因此，0.1 意味着只考虑包含前 10% 概率质量的代币。
     * 我们通常建议改变这个或 {@link CompletionRequest#temperature} 但不要两者兼而有之。
     */
    Double topP;

    /**
     * 默认值为 1
     * 为每个提示生成的完成次数。
     * 注意：由于此参数会生成许多完成，因此它会快速消耗令牌配额。谨慎使用并确保有合理的 {@link CompletionRequest#maxTokens}和 {@link CompletionRequest#stop} 设置.
     */
    Integer n;

    /**
     * 是否流式传输回部分进度。如果设置，令牌将在可用时作为纯数据服务器发送的事件发送，流由 data: [DONE] 消息终止。
     */
    Boolean stream;

    /**
     * 默认值为空
     * 包括 logprobs 最可能的令牌上的对数概率，以及所选令牌。
     * * 例如，如果 logprobs 为 5，则 API 将返回 5 个最可能的令牌的列表。
     * * API 将始终返回采样令牌的 logprob，因此响应中最多可能有 logprobs+1 个元素。logprob
     * <p>
     * logprobs最大值为 5。如果您需要更多，请通过我们的帮助中心与我们联系并描述您的使用案例。
     */
    Integer logprobs;

    /**
     * 除了完成之外，还回显提示
     */
    Boolean echo;

    /**
     * 最多 4 个序列，其中 API 将停止生成更多令牌。返回的文本将不包含停止序列。
     */
    List<String> stop;

    /**
     * 默认值为 0
     * 介于 -2.0 和 2.0 之间的数字。正值会根据新标记到目前为止是否出现在文本中来惩罚它们，从而增加模型讨论新主题的可能性。
     */
    Double presencePenalty;

    /**
     * 介于 -2.0 和 2.0 之间的数字。正值会根据新标记到目前为止在文本中的现有频率来惩罚新标记，从而降低模型逐字重复同一行的可能性。
     */
    Double frequencyPenalty;

    /**
     * 在服务器端生成best_of完成并返回“最佳”（每个令牌的日志概率最高的完成）。无法流式传输结果。
     * <p>
     * 当与 n 一起使用时，best_of 控制候选完成次数，n 指定要返回的数量 - best_of必须大于n
     * <p>
     * 注意：由于此参数会生成许多完成，因此它会快速消耗令牌配额。谨慎使用并确保有合理的max_tokens和stop设置.
     */
    Integer bestOf;

    /**
     * 修改完成中出现指定令牌的可能性。
     * <p>
     * 接受将令牌（由其在 GPT 标记器中的令牌 ID 指定）映射到 -100 到 100 之间的关联偏差值的 json 对象。您可以使用此分词器工具（适用于 GPT-2 和 GPT-3）将文本转换为令牌 ID。在数学上，偏差在采样之前被添加到模型生成的对数中。确切的效果因模型而异，但介于 -1 和 1 之间的值应降低或增加选择的可能性;像 -100 或 100 这样的值应该会导致禁止或排他性选择相关令牌。
     * <p>
     * 例如，您可以传递 {"50256": -100} 以防止生成 <|endoftext|> 令牌。
     */
    Map<String, Integer> logitBias;

    /**
     * 代表最终用户的唯一标识符，可帮助 OpenAI 监控和检测滥用行为
     */
    String user;
}
