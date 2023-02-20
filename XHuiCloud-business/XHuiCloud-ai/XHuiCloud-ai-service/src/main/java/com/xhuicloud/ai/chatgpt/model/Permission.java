package com.xhuicloud.ai.chatgpt.model;

import lombok.Data;

/**
 * @Desc 没有文档。。
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class Permission {

    public String id;

    public String object;

    public long created;

    public boolean allowCreateEngine;

    public boolean allowSampling;

    public boolean allowLogProbs;

    public boolean allowSearchIndices;

    public boolean allowView;

    public boolean allowFineTuning;

    public String organization;

    public String group;

    public boolean isBlocking;

}
