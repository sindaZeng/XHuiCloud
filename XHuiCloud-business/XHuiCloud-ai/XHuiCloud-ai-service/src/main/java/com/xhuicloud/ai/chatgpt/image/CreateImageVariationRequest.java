package com.xhuicloud.ai.chatgpt.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Desc 请求OpenAi创建一个图像的变体 所有字段都是可选的
 * @Url https://beta.openai.com/docs/api-reference/images/create-variation
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateImageVariationRequest {

    /**
     * 要生成的图像数量。必须在1到10之间。默认值为1。
     */
    Integer n;

    /**
     * 生成图像的大小。必须是256x256、512x512或1024x1024中的一个。默认为“1024x1024”。
     */
    String size;

    /**
     * 所生成图像的返回格式。必须是url或b64_json之一。默认为url。
     */
    String responseFormat;

    /**
     * 代表终端用户的唯一标识符，这将帮助OpenAI监控和检测滥用。
     */
    String user;
}
