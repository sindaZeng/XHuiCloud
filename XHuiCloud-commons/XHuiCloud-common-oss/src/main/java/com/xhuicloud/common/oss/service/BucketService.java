package com.xhuicloud.common.oss.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import lombok.AllArgsConstructor;
import java.util.List;

@AllArgsConstructor
public class BucketService {

    private final AmazonS3 amazonS3;

    /**
     * 创建 bucket
     * @param bucketName
     */
    public void createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket((bucketName));
        }
    }

    /**
     * 删除单个 bucket
     * @param bucketName
     */
    public void deleteBucket(String bucketName) {
        amazonS3.deleteBucket(bucketName);
    }

    /**
     * 获取 bucket列表
     * @return
     */
    public List<Bucket> getAllBuckets() {
        return amazonS3.listBuckets();
    }

    /**
     * 查询单个 bucket
     * @param bucketName
     * @return
     */
    public Bucket getBucket(String bucketName) {
        return amazonS3.listBuckets().stream()
                .filter(b -> b.getName().equals(bucketName))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Bucket not found!"));
    }
}
