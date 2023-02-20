package com.xhuicloud.ai.chatgpt.embedding;

import com.xhuicloud.ai.chatgpt.model.Usage;
import lombok.Data;

import java.util.List;

/**
 * @Desc 一个包含answer api响应的对象
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class EmbeddingResult {
    /**
     * 用于生成嵌入的GPT-3模型
     */
    String model;

    /**
     * 返回的对象类型应该是"list"
     */
    String object;

    /**
     * 计算嵌入的列表
     */
    List<Embedding> data;

    /**
     * 此请求的API使用情况
     */
    Usage usage;


}
