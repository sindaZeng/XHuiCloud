package com.xhuicloud.ai.chatgpt.model;

/**
 * @Desc 删除对象时的响应
 * @Author Sinda
 * @Date 2023/2/10
 */
public class DeleteResult {

    String id;

    /**
     * 删除的对象类型，例如“文件”或“模型”
     */
    String object;

    /**
     * 是否成功
     */
    boolean deleted;

}
