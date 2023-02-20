package com.xhuicloud.ai.chatgpt.embedding;

import lombok.Data;

import java.util.List;

/**
 * @Desc
 * @Url https://platform.openai.com/docs/guides/embeddings/what-are-embeddings
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class Embedding {

    /**
     * 返回的对象类型应该是"embedding"
     */
    String object;

    /**
     * 嵌入向量
     */
    List<Double> embedding;

    /**
     * 这个嵌入在列表中的位置
     */
    Integer index;
}
