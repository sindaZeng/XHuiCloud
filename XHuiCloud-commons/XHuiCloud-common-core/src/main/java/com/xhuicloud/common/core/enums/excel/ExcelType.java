package com.xhuicloud.common.core.enums.excel;

/**
 * @program: XHuiCloud
 * @description: Type
 * @author: Sinda
 * @create: 2020-03-22 16:54
 */
public enum ExcelType {
    ALL(0), EXPORT(1), IMPORT(2);
    private final int value;

    ExcelType(int value)
    {
        this.value = value;
    }

    public int value()
    {
        return this.value;
    }
}
