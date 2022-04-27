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

package com.xhuicloud.common.oss.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import lombok.AllArgsConstructor;
import lombok.Cleanup;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@AllArgsConstructor
public class OssService {

    private final AmazonS3 amazonS3;

    /**
     *
     * @param bucketName 存储桶
     * @param fileName 文件名
     * @param stream 文件流
     * @param contextType 文件类型
     * @throws Exception
     */
    public PutObjectResult upload(String bucketName, String fileName, InputStream stream, String contextType)
            throws Exception {
        return upload(bucketName, fileName, stream, stream.available(), contextType);
    }

    /**
     *
     * @param bucketName 存储桶
     * @param fileName 文件名
     * @param stream 文件流
     * @param size 大小
     * @param contextType 类型
     * @return
     * @throws Exception
     */
    public PutObjectResult upload(String bucketName, String fileName, InputStream stream, long size,
                                     String contextType) throws Exception {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        objectMetadata.setContentType(contextType);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(IOUtils.toByteArray(stream));
        return amazonS3.putObject(bucketName, fileName, byteArrayInputStream, objectMetadata);
    }

    /**
     * 删除文件
     * @param bucketName
     * @param fileName
     */
    public void remove(String bucketName, String fileName) {
        amazonS3.deleteObject(bucketName, fileName);
    }

    /**
     * 获取文件
     * @param bucketName
     * @param fileName
     * @return
     * @throws Exception
     */
    public S3Object getFile(String bucketName, String fileName) throws Exception {
        @Cleanup
        S3Object s3Object = amazonS3.getObject(bucketName, fileName);
        return s3Object;
    }

}
