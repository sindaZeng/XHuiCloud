package com.zsinda.fdp.service;

import com.zsinda.fdp.entity.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface SysFileService extends IService<SysFile>{

    /**
     * 上传文件
     * @param file
     * @param fileType
     * @return
     */
    String upload(MultipartFile file, String fileType);

    Boolean deleteFileById(Integer id);
}
