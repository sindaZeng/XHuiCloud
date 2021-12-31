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
