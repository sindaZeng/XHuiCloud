package com.xhuicloud.ai.chatgpt.edit;

import com.xhuicloud.ai.chatgpt.model.Usage;
import lombok.Data;

import java.util.List;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class EditResult {
    /**
     * 返回的对象类型应该是"edit"
     */
    public String object;

    /**
     * 毫秒为单位的创建时间
     */
    public long created;

    /**
     * 生成的编辑列表
     */
    public List<EditChoice> choices;

    /**
     * 此请求的API使用情况
     */
    public Usage usage;

}
