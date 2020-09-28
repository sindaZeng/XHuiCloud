package com.xhuicloud.common.lock.operation;

import lombok.AllArgsConstructor;
import org.redisson.api.RBinaryStream;
import org.redisson.api.RListMultimap;
import org.redisson.api.RedissonClient;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @program: XHuiCloud
 * @description: 操作对象二进制
 * @author: Sinda
 * @create: 2020-02-04 10:46
 */
@AllArgsConstructor
public class RedissonBinary {

    private final RedissonClient redisson;

    /**
     * 获取输出流
     *
     * @param name
     * @return
     */
    public OutputStream getOutputStream(String name) {
        RListMultimap<Object, Object> listMultimap = redisson.getListMultimap("");
        RBinaryStream binaryStream = redisson.getBinaryStream(name);
        return binaryStream.getOutputStream();
    }

    /**
     * 获取输入流
     *
     * @param name
     * @return
     */
    public InputStream getInputStream(String name) {
        RBinaryStream binaryStream = redisson.getBinaryStream(name);
        return binaryStream.getInputStream();
    }

    /**
     * 获取输入流
     *
     * @param name
     * @return
     */
    public InputStream getValue(String name, OutputStream stream) {
        try {
            RBinaryStream binaryStream = redisson.getBinaryStream(name);
            InputStream inputStream = binaryStream.getInputStream();
            byte[] buff = new byte[1024];
            int len;
            while ((len = inputStream.read(buff)) != -1) {
                stream.write(buff, 0, len);
            }
            return binaryStream.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取对象空间
     *
     * @param name
     * @return
     */
    public RBinaryStream getBucket(String name) {
        return redisson.getBinaryStream(name);
    }

    /**
     * 设置对象的值
     *
     * @param name  键
     * @param value 值
     * @return
     */
    public void setValue(String name, InputStream value) {
        try {
            RBinaryStream binaryStream = redisson.getBinaryStream(name);
            binaryStream.delete();
            OutputStream outputStream = binaryStream.getOutputStream();
            byte[] buff = new byte[1024];
            int len;
            while ((len = value.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除对象
     *
     * @param name 键
     * @return true 删除成功,false 不成功
     */
    public Boolean delete(String name) {
        RBinaryStream binaryStream = redisson.getBinaryStream(name);
        return binaryStream.delete();
    }


}
