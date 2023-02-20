package com.xhuicloud.ai.chatgpt.moderation;

import lombok.Data;

import java.util.List;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class ModerationResult {
    /**
     * 分配给该审核的唯一id。
     */
    public String id;

    /**
     * 模型
     */
    public String model;

    public List<Moderation> results;
}
