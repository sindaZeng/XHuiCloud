package com.xhuicloud.ai.payload;

import lombok.Data;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/16
 */
@Data
public class MessagePayload {

    private String messageId;

    private String userId;

    private String text;

}
