package com.zsinda.fdp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @program: FDPlatform
 * @author: Sinda
 * @create: 2020-02-04 11:05
 */
@Slf4j
@AllArgsConstructor
public class RedissonLock {


    private final Redisson redisson;


    /**
     * 加锁操作 （设置锁的有效时间）
     *
     * @param lockName  锁名称
     * @param leaseTime 锁有效时间
     */
    public void lock(String lockName, long leaseTime) {
        RLock rLock = redisson.getLock(lockName);
        rLock.lock(leaseTime, TimeUnit.SECONDS);
    }

    /**
     * 加锁操作 (锁有效时间采用默认时间30秒）
     *
     * @param lockName 锁名称
     */
    public void lock(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        rLock.lock();
    }

    /**
     * 加锁操作(tryLock锁，没有等待时间）
     *
     * @param lockName  锁名称
     * @param leaseTime 锁有效时间
     */
    public boolean tryLock(String lockName, long leaseTime) {

        RLock rLock = redisson.getLock(lockName);
        boolean getLock = false;
        try {
            getLock = rLock.tryLock(leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("获取Redisson分布式锁[异常]，lockName=" + lockName, e);
            e.printStackTrace();
            return false;
        }
        return getLock;
    }

    /**
     * 加锁操作(tryLock锁，有等待时间）
     *
     * @param lockName  锁名称
     * @param leaseTime 锁有效时间
     * @param waitTime  等待时间
     */
    public boolean tryLock(String lockName, long leaseTime, long waitTime) {

        RLock rLock = redisson.getLock(lockName);
        boolean getLock;
        try {
            getLock = rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("获取Redisson分布式锁[异常]，lockName=" + lockName, e);
            e.printStackTrace();
            return false;
        }
        return getLock;
    }

    /**
     * 解锁
     *
     * @param lockName 锁名称
     */
    public void unlock(String lockName) {
        redisson.getLock(lockName).unlock();
    }

    /**
     * 判断该锁是否已经被线程持有
     *
     * @param lockName 锁名称
     */
    public boolean isLock(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        return rLock.isLocked();
    }


    /**
     * 判断该线程是否持有当前锁
     *
     * @param lockName 锁名称
     */
    public boolean isHeldByCurrentThread(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        return rLock.isHeldByCurrentThread();
    }

}
