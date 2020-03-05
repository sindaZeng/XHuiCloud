package com.zsinda.fdp.base;

/**
 * @program: FDPlatform
 * @description: 分布式 id生成器基类
 * @author: Sinda
 * @create: 2020-03-04 17:41
 */
public interface IDGenerate {

    long get();

    Boolean init();
}
