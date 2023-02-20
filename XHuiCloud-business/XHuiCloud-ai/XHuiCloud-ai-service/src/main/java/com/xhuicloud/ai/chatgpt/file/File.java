package com.xhuicloud.ai.chatgpt.file;

import lombok.Data;

/**
 * @Desc 文件用于上传可与微调等功能一起使用的文档.
 * @Url https://beta.openai.com/docs/api-reference/files
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class File {


    /**
     * 此文件的唯一id.
     */
    String id;

    /**
     * 返回的对象类型应该是"file"
     */
    String object;

    /**
     * 文件大小(字节)
     */
    Long bytes;

    /**
     * 秒为单位的创建时间
     */
    Long createdAt;

    /**
     * 文件的名称。
     */
    String filename;

    /**
     * 文件用途的描述。
     */
    String purpose;
}
