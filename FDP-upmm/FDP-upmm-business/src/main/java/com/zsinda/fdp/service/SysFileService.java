package com.zsinda.fdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsinda.fdp.entity.SysFile;
import com.zsinda.fdp.vo.FileVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface SysFileService extends IService<SysFile>{

    /**
     * 上传文件
     * @param file
     * @param fileType
     * @return
     */
    String upload(MultipartFile file, String fileType);

    Boolean deleteFileById(Integer id);

    void download(String fileName, HttpServletResponse response);

    FileVo detail(Integer id);
}
