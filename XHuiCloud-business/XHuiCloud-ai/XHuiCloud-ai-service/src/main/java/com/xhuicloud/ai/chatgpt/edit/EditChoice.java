package com.xhuicloud.ai.chatgpt.edit;

import lombok.Data;

/**
 * @Desc 为提供的输入、指令和参数创建新的编辑。
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class EditChoice {

    /**
     * 编辑后的文本。
     */
    String text;

    /**
     * 该补全在返回列表中的索引
     */
    Integer index;

}
