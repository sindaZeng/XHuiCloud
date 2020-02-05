package com.zsinda.fdp.annotation;

/**
 * @program: FDPlatform
 * @description: 分布式锁注解
 * @author: Sinda
 * @create: 2020-02-04 10:00
 */
public @interface FdpLock {

    /**
     * 锁的名称,默认方法名 && 支持springEl表达式
     */
    String value() default "";

    /**
     * 前缀: "默认方法名" + "-"
     */
    String prefix() default "";

    /**
     * 锁的有效时间,单位秒
     */
    int leaseTime() default 20;

    /**
     * lock()方法是一个无条件的锁
     * tryLock()方法只有在成功获取了锁的情况下才会返回true，
     * 如果别的线程当前正持有锁，则会立即返回false！如果为这个方法加上timeout参数，
     * 则会在等待timeout的时间才会返回false或者在获取到锁的时候返回true。
     */
    boolean isUserTryLock() default false;
}
