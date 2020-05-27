package com.zsinda.fdp.enums.excel;

/**
 * @program: FDPlatform
 * @description: Type
 * @author: Sinda
 * @create: 2020-03-22 16:54
 */
public enum Type {
    ALL(0), EXPORT(1), IMPORT(2);
    private final int value;

    Type(int value)
    {
        this.value = value;
    }

    public int value()
    {
        return this.value;
    }
}
