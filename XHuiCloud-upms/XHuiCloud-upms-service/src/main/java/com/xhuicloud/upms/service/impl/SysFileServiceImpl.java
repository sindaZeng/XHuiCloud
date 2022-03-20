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

package com.xhuicloud.upms.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.common.oss.properties.OssProperties;
import com.xhuicloud.common.oss.service.OssService;
import com.xhuicloud.upms.entity.SysFile;

import com.xhuicloud.upms.mapper.SysFileMapper;
import com.xhuicloud.upms.service.SysFileService;

import com.xhuicloud.upms.vo.FileVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    private final OssProperties ossProperties;

    private final OssService ossService;

    @Override
    public String upload(MultipartFile file) {
        if (StrUtil.isBlank(file.getOriginalFilename())) {
            throw SysException.sysFail("上传文件错误，文件为空!");
        }
        try {
            String fileType = FileUtil.extName(file.getOriginalFilename());
            String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
            String url = StrUtil.SLASH + ossProperties.getBucketName() + StrUtil.SLASH + fileName;

            ossService.upload(ossProperties.getBucketName(), fileName, file.getInputStream(),
                    file.getContentType());
            SysFile sysFile = new SysFile();
            sysFile.setFileSize(file.getSize());
            sysFile.setName(file.getOriginalFilename());
            sysFile.setFileName(fileName);
            sysFile.setUrl(url);
            sysFile.setFileType(fileType);
            sysFile.setBucketName(ossProperties.getBucketName());
            save(sysFile);
            return url;
        }
		catch (Exception e) {
            log.error("上传失败", e);
        }
        return "";
    }

    @Override
    public Boolean deleteFileById(Integer id) {
        SysFile sysFile = getById(id);
        ossService.remove(sysFile.getBucketName(), sysFile.getFileName());
        return removeById(id);
    }

    @Override
    public void download(String fileName, HttpServletResponse response) {

    }

    @Override
    public FileVo detail(Integer id) {
        return null;
    }

//    @Autowired
//    private SysParamService sysParamService;
//
//    SysFileServiceImpl(BucketProperties bucketProperties) {
//        Configuration cfg = new Configuration(Region.region2());
//        uploadManager = new UploadManager(cfg);
//        auth = Auth.create(bucketProperties.getAccessKey(), bucketProperties.getSecretKey());
//        bucketManager = new BucketManager(auth, cfg);
//        this.bucketProperties = bucketProperties;
//    }
//
//    @Override
//    @SysLog("上传文件")
//    @Transactional(rollbackFor = Exception.class)
//    public String upload(MultipartFile file, String fileType) {
//
//        if (StringUtils.isBlank(file.getOriginalFilename())) {
//            throw SysException.sysFail("上传文件错误，文件为空!");
//        }
//        // 文件名称
//        String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
//        String upToken = auth.uploadToken(bucketProperties.getBucketName());
//        try {
//            Response response = uploadManager.put(file.getBytes(), fileName, upToken);
//            if (response.statusCode != HttpStatus.HTTP_OK) {
//                throw SysException.sysFail("上传文件错误，请重新上传或联系管理员!");
//            }
//            // 保存文件属性 方便管理文件
//            SysFile sysFile = new SysFile();
//            sysFile.setFileSize(file.getSize());
//            sysFile.setName(file.getOriginalFilename());
//            sysFile.setFileName(fileName);
//            sysFile.setUrl(fileName);
//            sysFile.setFileType(fileType);
//            sysFile.setCreateId(SecurityHolder.getUser().getId());
//            sysFile.setBucketName(bucketProperties.getBucketName());
//            save(sysFile);
//            return fileName;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    @SysLog("删除文件")
//    @Transactional(rollbackFor = Exception.class)
//    public Boolean deleteFileById(Integer id) {
//        SysFile sysFile = getById(id);
//        if (ObjectUtil.isNull(sysFile)) {
//            throw SysException.sysFail("文件不存在!");
//        }
//        try {
//            int delete = getBaseMapper().deleteById(id);
//            Response response = bucketManager.delete(bucketProperties.getBucketName(), sysFile.getFileName());
//            if (response.statusCode != HttpStatus.HTTP_OK) {
//                throw SysException.sysFail("删除文件错误，请重新删除或联系管理员!");
//            }
//            return delete > 0;
//        } catch (QiniuException ex) {
//            //如果遇到异常，说明删除失败
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public void download(String fileName, HttpServletResponse response) {
//        try {
//            SysFile sysFile = getOne(Wrappers.<SysFile>lambdaQuery().eq(SysFile::getFileName, fileName));
//            if (ObjectUtil.isNull(sysFile)) {
//                throw SysException.sysFail("下载文件错误，文件不存在!");
//            }
//            SysParam sysConfigByKey = sysParamService.getSysParamByKey(SysConfigConstants.SYS_CDN_DEFAULT_DOMAIN);
//            HttpUtil.download(sysConfigByKey.getParamValue() + sysFile.getUrl(), response.getOutputStream(), true);
//        } catch (IOException e) {
//            log.error("文件下载异常: {}", e.getLocalizedMessage());
//        }
//    }
//
//    @Override
//    public FileVo detail(Integer id) {
//        return baseMapper.detail(id, XHuiTenantHolder.getTenant());
//    }
}
