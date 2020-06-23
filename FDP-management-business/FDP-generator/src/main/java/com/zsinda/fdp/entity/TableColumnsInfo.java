package com.zsinda.fdp.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: FDPlatform
 * @description: 表字段信息
 * @author: Sinda
 * @create: 2020-06-22 12:14
 */
@Data
public class TableColumnsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据库原字段名 例如 create_time
     */
    private String columnName;

    /**
     * 数据库字段名 小驼峰 例如 createTime
     */
    private String smallColumnName;

    /**
     * 数据原类型
     */
    private String dataType;

    /**
     * 数据库类型 对应java
     */
    private String javaDataType;

    /**
     * 字段描述
     */
    private String columnComment;

    /**
     * 其他信息
     */
    private String extra;

    /**
     * 列键
     */
    private String columnKey;

    /**
     * 是否能为空
     */
    private String isNullable;

}
