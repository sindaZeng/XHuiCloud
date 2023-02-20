package com.xhuicloud.ai.chatgpt.model;

import lombok.Data;

import java.util.List;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class Model {
    /**
     * 此模型的标识符，用于在执行补全等操作时指定模型
     */
    public String id;

    /**
     * 返回的对象类型应该是"model"
     */
    public String object;

    /**
     * GPT-3模型的所有者，通常是“openai”
     */
    public String ownedBy;

    /**
     * 此模型的权限列表
     */
    public List<Permission> permission;

    /**
     * this及其父模型(如果适用)所基于的根模型
     */
    public String root;

    /**
     * 父模型
     */
    public String parent;
}