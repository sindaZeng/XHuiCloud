package com.xhuicloud.common.zero.base;

/**
 * @program: XHuiCloud
 * @description: 分布式 id生成器基类
 * @author: Sinda
 * @create: 2020-03-04 17:41
 */
public interface IDGenerate {

    long get();

    Boolean init();
}
