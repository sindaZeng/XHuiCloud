package com.zsinda.fdp.operation;

import com.zsinda.fdp.properties.FdpRedisProperties;
import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @program: FDPlatform
 * @description: 操作对象
 * @author: Sinda
 * @create: 2020-02-04 10:47
 */
@AllArgsConstructor
public class RedissonObject {

    private final RedissonClient redisson;
    private final FdpRedisProperties fdpRedisProperties;

    /**
     * 获取对象值
     *
     * @param name
     * @param <T>
     * @return
     */
    public <T> T getValue(String name) {
        RBucket<T> bucket = redisson.getBucket(name);
        return bucket.get();
    }

    /**
     * 获取对象空间
     *
     * @param name
     * @param <T>
     * @return
     */
    public <T> RBucket<T> getBucket(String name) {
        return redisson.getBucket(name);
    }

    /**
     * 设置对象的值
     *
     * @param name  键
     * @param value 值
     * @return
     */
    public <T> void setValue(String name, T value) {
        setValue(name, value, fdpRedisProperties.getDataValidTime());
    }

    /**
     * 设置对象的值
     *
     * @param name  键
     * @param value 值
     * @param time  缓存时间 单位毫秒 -1 永久缓存
     * @return
     */
    public <T> void setValue(String name, T value, Long time) {
        RBucket<Object> bucket = redisson.getBucket(name);
        if (time == -1) {
            bucket.set(value);
        } else {
            bucket.set(value, time, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 如果值已经存在则则不设置
     *
     * @param name  键
     * @param value 值
     * @param time  缓存时间 单位毫秒
     * @return true 设置成功,false 值存在,不设置
     */
    public <T> Boolean trySetValue(String name, T value, Long time) {
        RBucket<Object> bucket = redisson.getBucket(name);
        boolean b;
        if (time == -1) {
            b = bucket.trySet(value);
        } else {
            b = bucket.trySet(value, time, TimeUnit.MILLISECONDS);
        }
        return b;
    }

    /**
     * 如果值已经存在则则不设置
     *
     * @param name  键
     * @param value 值
     * @return true 设置成功,false 值存在,不设置
     */
    public <T> Boolean trySetValue(String name, T value) {
        return trySetValue(name, value, fdpRedisProperties.getDataValidTime());
    }

    /**
     * 删除对象
     *
     * @param name 键
     * @return true 删除成功,false 不成功
     */
    public Boolean delete(String name) {
        return redisson.getBucket(name).delete();
    }


}
