package com.zsinda.fdp.entity;

import lombok.Data;

/**
 * @program: FDPlatform
 * @description: 生成信息
 * @author: Sinda
 * @create: 2020-05-07 18:14
 */
@Data
public class GeneratorInfo {

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
