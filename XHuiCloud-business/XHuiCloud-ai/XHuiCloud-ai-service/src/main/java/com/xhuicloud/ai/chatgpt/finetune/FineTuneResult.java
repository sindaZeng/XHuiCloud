package com.xhuicloud.ai.chatgpt.finetune;

import com.xhuicloud.ai.chatgpt.file.File;
import lombok.Data;

import java.util.List;

/**
 * @Desc 管理微调作业，以根据特定训练数据定制模型。
 * @Url  https://beta.openai.com/docs/api-reference/fine-tunes
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class FineTuneResult {

    /**
     * 微调作业的ID
     */
    String id;

    /**
     * 返回的对象类型，应该是“fine-tune”
     */
    String object;

    /**
     * 基本模型的名称
     */
    String model;

    /**
     * 以秒为单位的创建时间。
     */
    Long createdAt;

    /**
     * 此作业生命周期中的事件列表。获取微调作业列表时为空
     */
    List<FineTuneEvent> events;

    /**
     * 微调后的模型ID，未完成微调则为null。
     * 这是用于调用模型的 id。
     */
    String fineTunedModel;

    /**
     * 调整作业的指定超参数。
     */
    HyperParameters hyperParameters;

    /**
     * 此模型所属组织的 ID
     */
    String organizationId;

    /**
     * 此微调作业的结果文件。
     */
    List<File> resultFiles;

    /**
     * 微调作业的状态。“待定”、“成功”或“已取消”
     */
    String status;

    /**
     * 此微调作业的训练文件。
     */
    List<File> trainingFiles;

    /**
     * 以纪元秒为单位的最后更新时间
     */
    Long updatedAt;

    /**
     * 此微调作业的验证文件
     */
    List<File> validationFiles;

}
