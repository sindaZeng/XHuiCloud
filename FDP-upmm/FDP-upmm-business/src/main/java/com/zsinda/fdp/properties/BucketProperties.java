package com.zsinda.fdp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: FDPlatform
 * @description: ElasticJobProperties
 * @author: Sinda
 * @create: 2020-03-05 23:20
 */
@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class BucketProperties {

    /**
     * AK
     */
    private String accessKey;

    /**
     * SK
     */
    private String secretKey;

    /**
     * 桶名称
     */
    private String bucketName;

}
