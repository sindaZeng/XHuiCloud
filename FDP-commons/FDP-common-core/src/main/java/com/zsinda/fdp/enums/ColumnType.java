package com.zsinda.fdp.enums;

/**
 * @program: FDPlatform
 * @description: ColumnType
 * @author: Sinda
 * @create: 2020-03-22 17:35
 */
public enum ColumnType {
    NUMERIC(0), STRING(1);
    private final int value;

    ColumnType(int value)
    {
        this.value = value;
    }

    public int value()
    {
        return this.value;
    }
}
