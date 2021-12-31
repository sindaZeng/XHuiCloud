/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.common.lock.annotation;

import java.lang.annotation.*;

/**
 * @program: XHuiCloud
 * @description: 分布式锁注解
 * @author: Sinda
 * @create: 2020-02-04 10:00
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface XHuiLock {

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
     * 锁的等待时间,单位秒
     */
    long waitTime() default 30L;

    /**
     * lock()方法是一个无条件的锁
     * tryLock()方法只有在成功获取了锁的情况下才会返回true，
     * 如果别的线程当前正持有锁，则会立即返回false！如果为这个方法加上timeout参数，
     * 则会在等待timeout的时间才会返回false或者在获取到锁的时候返回true。
     */
    boolean isUserTryLock() default false;
}
