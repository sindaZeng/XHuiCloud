package com.zsinda.fdp.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.zsinda.fdp.exception.SysException;
import com.zsinda.fdp.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: FDPlatform
 * @description: SysFileController
 * @author: Sinda
 * @create: 2020-03-26 14:04
 */
@RestController
@RequestMapping("/file")
@Api(value = "file",tags = "文件管理模块")
@RefreshScope
public class SysFileController {

    /**
     * 七牛AK
     */
    @Value("${qiniu.accessKey}")
    private String accessKey;

    /**
     * 七牛SK
     */
    @Value("${qiniu.secretKey}")
    private String secretKey;

    /**
     * 七牛桶
     */
    @Value("${qiniu.bucket}")
    private String bucket;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R upload(MultipartFile file){

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
            //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(file.getBytes(), fileName, upToken);
                if (response.statusCode != HttpStatus.HTTP_OK){
                    throw SysException.sysFail("上传文件错误，请重新上传或联系管理员!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return R.ok(fileName);
    }
}
