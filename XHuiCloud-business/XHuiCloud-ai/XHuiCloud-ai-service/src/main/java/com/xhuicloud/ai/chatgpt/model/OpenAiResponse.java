package com.xhuicloud.ai.chatgpt.model;

import lombok.Data;

import java.util.List;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class OpenAiResponse<T> {
    /**
     * 结果列表
     */
    public List<T> data;

    /**
     * 返回的对象类型应该是"list"
     */
    public String object;
}
