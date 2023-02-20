package com.xhuicloud.ai.chatgpt.embedding;

import lombok.*;

import java.util.List;

/**
 * @Desc 创建表示输入文本的嵌入向量。
 * @Url https://beta.openai.com/docs/api-reference/embeddings/create
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddingRequest {

    /**
     * 要使用的模型名称。
     * 如果使用新的v1/embeddings端点 不能为空
     */
    String model;

    /**
     * 输入文本以获取嵌入，编码为字符串或令牌数组。
     * 要在单个请求中获得多个输入的嵌入，请传递一个字符串数组或令牌数组数组。
     * 每个输入的token长度不能超过2048个。
     * <p>
     * 除非你是嵌入代码，否则我们建议将输入中的换行符(\n)替换为一个空格，
     * 当换行符出现时，我们观察到较差的结果。
     */
    @NonNull
    List<String> input;

    /**
     * 代表终端用户的唯一标识符
     */
    String user;

}
