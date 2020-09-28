package com.xhuicloud.generator.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: XHuiCloud
 * @description: TableInfo
 * @author: Sinda
 * @create: 2020-06-22 12:08
 */
@Data
public class TableInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 存储引擎
     */
    private String engine;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
