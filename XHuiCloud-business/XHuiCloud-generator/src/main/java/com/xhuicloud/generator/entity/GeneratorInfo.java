package com.xhuicloud.generator.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: XHuiCloud
 * @description: 生成信息
 * @author: Sinda
 * @create: 2020-05-07 18:14
 */
@Data
public class GeneratorInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 作者
     */
    private String author;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表备注
     */
    private String comments;

    /**
     * 代码风格
     * 0 - avue
     * 1 - element
     */
    private String style;
}
