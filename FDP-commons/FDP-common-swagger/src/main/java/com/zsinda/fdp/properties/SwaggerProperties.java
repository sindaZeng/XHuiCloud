package com.zsinda.fdp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: FDPlatform
 * @description: SwaggerProperties
 * @author: Sinda
 * @create: 2019-12-25 22:36
 **/
@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {

    /**
     * swagger解析包路径
     **/
    private String basePackage = "";

    /**
     * 标题
     **/
    private String title = "";

    /**
     * 描述
     **/
    private String description = "";

    /**
     * 版本
     **/
    private String version = "";

    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";

    /**
     * 联系人
     **/
    private String name = "";

    /**
     * 联系人url
     **/
    private String url = "";

    /**
     * 联系人email
     **/
    private String email = "";


}
