package com.xhuicloud.ai.chatgpt.moderation;

import lombok.*;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModerationRequest {

    /**
     * 输入文本进行分类。
     */
    @NonNull
    String input;

    /**
     * 要使用的模型名称，默认为text-moderation-stable。
     */
    String model;
}
