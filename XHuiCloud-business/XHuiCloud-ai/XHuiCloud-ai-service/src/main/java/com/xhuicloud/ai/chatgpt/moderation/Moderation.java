package com.xhuicloud.ai.chatgpt.moderation;

import lombok.Data;

/**
 * @Desc 分类文本是否违反 OpenAI 的内容政策
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class Moderation {

    /**
     * 如果模型将内容分类为违反OpenAI的内容策略，则设置为true，否则为false
     */
    public boolean flagged;

    /**
     * 对象，其中包含每个类别的二进制内容策略违反标志。
     * 对于每个类别，如果模型将相应类别标记为违规，则该值为true，否则为false。
     */
    public ModerationCategories categories;

    /**
     * 对象，包含模型输出的每个类别的原始分数，表示模型对数据的置信度
     * 输入违反了OpenAI对类别的政策。
     * 该值介于0和1之间，其中值越大表示置信度越高。
     * 分数不应被解释为概率。
     */
    public ModerationCategoryScores categoryScores;
}
