package com.xhuicloud.ai.chatgpt.finetune;

import lombok.Data;

/**
 * @Desc 管理微调作业，以根据特定训练数据定制模型。
 * @Url https://beta.openai.com/docs/api-reference/fine-tunes
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class FineTuneEvent {

    /**
     * 返回的对象类型应为"fine-tune-event"。
     */
    String object;

    /**
     * 秒为单位的创建时间。
     */
    Long createdAt;

    /**
     * 该消息的日志级别。
     */
    String level;

    /**
     * 事件消息。
     */
    String message;

}
