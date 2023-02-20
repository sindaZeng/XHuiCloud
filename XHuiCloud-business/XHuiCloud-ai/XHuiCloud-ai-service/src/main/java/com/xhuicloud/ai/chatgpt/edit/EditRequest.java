package com.xhuicloud.ai.chatgpt.edit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Desc 给定提示和指令，OpenAi将返回提示的编辑版本
 * @Url https://beta.openai.com/docs/api-reference/edits/create
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditRequest {

    /**
     * 要使用的模型名称
     * 如果使用新的v1/edit端点则必填
     */
    String model;

    /**
     * 用作编辑起始点的输入文本。
     */
    String input;

    /**
     * 告诉模型如何编辑提示的指令。
     * 例如，"修复拼写错误"
     */
    @NonNull
    String instruction;

    /**
     * 为输入和指令生成多少编辑。
     */
    Integer n;

    /**
     使用什么采样温度，介于 0 和 2 之间。较高的值（如 0.8）将使输出更加随机，而较低的值（如 0.2）将使其更加集中和确定。
     ＊
     *我们通常建议修改这个或{@link EditRequest#topP}，但不要同时修改
     */
    Double temperature;

    /**
     使用温度采样的替代方法称为核心采样，其中模型考虑具有top_p概率质量的令牌的结果。因此，0.1 意味着只考虑包含前 10% 概率质量的代币。
     *
     * top_p
     * 数
     * 自选
     * 默认值为 1
     * 使用温度采样的替代方法称为核心采样，其中模型考虑具有top_p概率质量的令牌的结果。因此，0.1 意味着只考虑包含前 10% 概率质量的代币。
     *
     * 我们通常建议改变这个或 {@link EditRequest#temperature} 但不要两者兼而有之。
     */
    @JsonProperty("top_p")
    Double topP;

}
