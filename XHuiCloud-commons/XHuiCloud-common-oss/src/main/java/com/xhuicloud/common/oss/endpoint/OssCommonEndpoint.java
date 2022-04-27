package com.xhuicloud.common.oss.endpoint;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.xhuicloud.common.oss.service.BucketService;
import com.xhuicloud.common.oss.service.OssService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/oss")
public class OssCommonEndpoint {

    private final OssService ossService;

    private final BucketService bucketService;

    /**
     * 创建 bucket
     *
     * @param bucketName
     */
    @PostMapping("/bucket/{bucketName}")
    public void createBucket(@PathVariable String bucketName) {
        bucketService.createBucket(bucketName);
    }

    /**
     * 根据bucketName 查询bucket
     *
     * @param bucketName
     * @return
     */
    @GetMapping("/bucket/{bucketName}")
    public Bucket bucket(@PathVariable String bucketName) {
        return bucketService.getBucket(bucketName);
    }

    /**
     * 获取所有bucket
     *
     * @return
     */
    @GetMapping("/buckets")
    public List<Bucket> buckets() {
        return bucketService.getAllBuckets();
    }

    /**
     * 根据名称删除bucket
     *
     * @param bucketName
     */
    @DeleteMapping("/bucket/{bucketName}")
    public void deleteBucket(@PathVariable String bucketName) {
        bucketService.deleteBucket(bucketName);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param bucketName
     * @return
     */
    @SneakyThrows
    @PostMapping("/upload/{bucketName}")
    public S3Object upload(@RequestBody MultipartFile file, @PathVariable String bucketName) {
        String name = file.getOriginalFilename();
        ossService.upload(bucketName, name, file.getInputStream(), file.getSize(), file.getContentType());
        return ossService.getFile(bucketName, name);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param bucketName
     * @param fileName
     * @return
     */
    @SneakyThrows
    @PostMapping("/upload/{bucketName}/{fileName}")
    public S3Object upload(@RequestBody MultipartFile file, @PathVariable String bucketName, @PathVariable String fileName) {
        ossService.upload(bucketName, fileName, file.getInputStream(), file.getSize(), file.getContentType());
        return ossService.getFile(bucketName, fileName);
    }


}
