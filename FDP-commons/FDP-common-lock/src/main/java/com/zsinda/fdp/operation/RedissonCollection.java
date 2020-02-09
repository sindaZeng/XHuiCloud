package com.zsinda.fdp.operation;

import com.zsinda.fdp.properties.FdpRedisProperties;
import lombok.AllArgsConstructor;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: FDPlatform
 * @description: 操作集合
 * @author: Sinda
 * @create: 2020-02-04 10:46
 */
@AllArgsConstructor
public class RedissonCollection {

    private final RedissonClient redisson;
    private final FdpRedisProperties fdpRedisProperties;

    /**
     * 获取map集合
     *
     * @param name
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> RMap<K, V> getMap(String name) {
        return redisson.getMap(name);
    }

    /**
     * 设置map集合
     *
     * @param name
     * @param data
     * @param time 缓存时间,单位毫秒 -1永久缓存
     * @return
     */
    public void setMapValues(String name, Map data, Long time) {
        RMap map = redisson.getMap(name);
        Long dataValidTime = fdpRedisProperties.getDataValidTime();
        if (time != -1) {
            map.expire(dataValidTime, TimeUnit.MILLISECONDS);
        }
        map.putAll(data);
    }

    /**
     * 设置map集合
     *
     * @param name
     * @param data
     * @return
     */
    public void setMapValues(String name, Map data) {
        setMapValues(name, data, fdpRedisProperties.getDataValidTime());
    }

    /**
     * 获取List集合
     *
     * @param name
     * @return
     */
    public <T> RList<T> getList(String name) {
        return redisson.getList(name);
    }

    /**
     * 设置List集合
     *
     * @param name
     * @param data
     * @param time 缓存时间,单位毫秒 -1永久缓存
     * @return
     */
    public void setListValues(String name, List data, Long time) {
        RList list = redisson.getList(name);
        Long dataValidTime = fdpRedisProperties.getDataValidTime();
        if (time != -1) {
            list.expire(dataValidTime, TimeUnit.MILLISECONDS);
        }
        list.addAll(data);
    }

    /**
     * 设置List集合
     *
     * @param name
     * @param data
     * @return
     */
    public void setListValues(String name, List data) {
        setListValues(name, data, fdpRedisProperties.getDataValidTime());
    }

    /**
     * 获取set集合
     *
     * @param name
     * @return
     */
    public <T> RSet<T> getSet(String name) {
        return redisson.getSet(name);
    }

    /**
     * 设置set集合
     *
     * @param name
     * @param data
     * @param time 缓存时间,单位毫秒 -1永久缓存
     * @return
     */
    public void setSetValues(String name, Set data, Long time) {
        RSet set = redisson.getSet(name);
        Long dataValidTime = fdpRedisProperties.getDataValidTime();
        if (time != -1) {
            set.expire(dataValidTime, TimeUnit.MILLISECONDS);
        }
        set.addAll(data);
    }

    /**
     * 设置set集合
     *
     * @param name
     * @param data
     * @return
     */
    public void setSetValues(String name, Set data) {
        setSetValues(name, data, fdpRedisProperties.getDataValidTime());
    }


}
