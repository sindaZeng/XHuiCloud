package com.xhuicloud.ai.chatgpt.finetune;

import lombok.Data;

/**
 * @Desc 微调作业 参数
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class HyperParameters {

    /**
     * 用于微调的批大小
     */
    String batchSize;

    /**
     * 用于训练的学习率乘数。
     */
    Double learningRateMultiplier;

    /**
     * 训练模型的epoch数。
     */
    Integer nEpochs;

    /**
     * 用于提示令牌损失的权重
     */
    Double promptLossWeight;
}
