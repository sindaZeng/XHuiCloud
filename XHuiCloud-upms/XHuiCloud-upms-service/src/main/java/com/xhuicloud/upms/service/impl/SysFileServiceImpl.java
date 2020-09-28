package com.xhuicloud.upms.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.xhuicloud.common.core.constant.SysConfigConstants;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.common.datasource.tenant.XHuiTenantHolder;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.common.security.utils.SecurityHolder;
import com.xhuicloud.upms.entity.SysFile;
import com.xhuicloud.upms.entity.SysParam;
import com.xhuicloud.upms.mapper.SysFileMapper;
import com.xhuicloud.upms.properties.BucketProperties;
import com.xhuicloud.upms.service.SysFileService;
import com.xhuicloud.upms.service.SysParamService;
import com.xhuicloud.upms.vo.FileVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    private final BucketProperties bucketProperties;

    private final UploadManager uploadManager;

    private final Auth auth;

    private final BucketManager bucketManager;

    @Autowired
    private SysParamService sysParamService;

    SysFileServiceImpl(BucketProperties bucketProperties) {
        Configuration cfg = new Configuration(Region.region2());
        uploadManager = new UploadManager(cfg);
        auth = Auth.create(bucketProperties.getAccessKey(), bucketProperties.getSecretKey());
        bucketManager = new BucketManager(auth, cfg);
        this.bucketProperties = bucketProperties;
    }

    @Override
    @SysLog("上传文件")
    @Transactional(rollbackFor = Exception.class)
    public String upload(MultipartFile file, String fileType) {

        if (StringUtils.isBlank(file.getOriginalFilename())) {
            throw SysException.sysFail("上传文件错误，文件为空!");
        }
        // 文件名称
        String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
        String upToken = auth.uploadToken(bucketProperties.getBucketName());
        try {
            Response response = uploadManager.put(file.getBytes(), fileName, upToken);
            if (response.statusCode != HttpStatus.HTTP_OK) {
                throw SysException.sysFail("上传文件错误，请重新上传或联系管理员!");
            }
            // 保存文件属性 方便管理文件
            SysFile sysFile = new SysFile();
            sysFile.setFileSize(file.getSize());
            sysFile.setName(file.getOriginalFilename());
            sysFile.setFileName(fileName);
            sysFile.setUrl(fileName);
            sysFile.setFileType(fileType);
            sysFile.setCreateId(SecurityHolder.getUser().getId());
            sysFile.setBucketName(bucketProperties.getBucketName());
            save(sysFile);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @SysLog("删除文件")
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteFileById(Integer id) {
        SysFile sysFile = getById(id);
        if (ObjectUtil.isNull(sysFile)) {
            throw SysException.sysFail("文件不存在!");
        }
        try {
            int delete = getBaseMapper().deleteById(id);
            Response response = bucketManager.delete(bucketProperties.getBucketName(), sysFile.getFileName());
            if (response.statusCode != HttpStatus.HTTP_OK) {
                throw SysException.sysFail("删除文件错误，请重新删除或联系管理员!");
            }
            return delete > 0;
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void download(String fileName, HttpServletResponse response) {
        try {
            SysFile sysFile = getOne(Wrappers.<SysFile>lambdaQuery().eq(SysFile::getFileName, fileName));
            if (ObjectUtil.isNull(sysFile)) {
                throw SysException.sysFail("下载文件错误，文件不存在!");
            }
            SysParam sysConfigByKey = sysParamService.getSysParamByKey(SysConfigConstants.SYS_CDN_DEFAULT_DOMAIN);
            HttpUtil.download(sysConfigByKey.getParamValue() + sysFile.getUrl(), response.getOutputStream(), true);
        } catch (IOException e) {
            log.error("文件下载异常: {}", e.getLocalizedMessage());
        }
    }

    @Override
    public FileVo detail(Integer id) {
        return baseMapper.detail(id, XHuiTenantHolder.getTenant());
    }
}
