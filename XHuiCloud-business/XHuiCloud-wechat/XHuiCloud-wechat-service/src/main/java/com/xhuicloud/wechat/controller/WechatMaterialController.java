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

package com.xhuicloud.wechat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.AuditRecord;
import com.xhuicloud.wechat.config.WeChatMpCommonService;
import com.xhuicloud.wechat.service.WechatMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;


/**
 * @program: wechat
 * @description:
 * @author: Sinda
 * @create: 2022-11-14 21:29:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/material/{appId}")
@Api(value = "material", tags = "素材管理")
public class WechatMaterialController {

    private final WechatMaterialService wechatMaterialService;

    /**
     * 分页查询
     *
     * @param page 分页对象
     * @return Response
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParam(name = "type", value = "语音:voice 图片:image 视频: video", required = true, dataTypeClass = WxConsts.MaterialType.class)
    public Response page(@PathVariable String appId, Page page, @RequestParam String type) throws WxErrorException {
        WxMpMaterialService materialService = getWxMpMaterialService(appId);
        WxMpMaterialFileBatchGetResult result = materialService.materialFileBatchGet(type,
                Long.valueOf((page.getCurrent() - 1) * page.getSize()).intValue(), Long.valueOf(page.getSize()).intValue());
        page.setRecords(result.getItems());
        page.setTotal(result.getTotalCount());
        return Response.success(page);
    }

    /**
     * 分页查询图文列表
     *
     * @param page 分页对象
     * @return Response
     */
    @SneakyThrows
    @GetMapping("/page/news")
    @ApiOperation(value = "分页查询图文列表", notes = "分页查询图文列表")
    public Response page(@PathVariable String appId, Page page) {
        WxMpMaterialService materialService = getWxMpMaterialService(appId);
        WxMpMaterialNewsBatchGetResult result = materialService.materialNewsBatchGet(Long.valueOf((page.getCurrent() - 1) * page.getSize()).intValue(),
                Long.valueOf(page.getSize()).intValue());
        page.setRecords(result.getItems());
        page.setTotal(result.getTotalCount());
        return Response.success(page);
    }

    /**
     * 新增图片语音素材
     *
     * @return Response
     */
    @AuditRecord("新增图片语音素材")
    @SneakyThrows
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_material')")
    @ApiOperation(value = "新增图片语音素材", notes = "新增图片语音素材")
    public Response save(@PathVariable String appId, @RequestParam String type,
                         @RequestPart("file") MultipartFile multipartFile) {
        WxMpMaterialService materialService = getWxMpMaterialService(appId);
        File file = getFile(multipartFile);
        multipartFile.transferTo(file);
        WxMpMaterial wxMpMaterial = new WxMpMaterial();
        wxMpMaterial.setName(file.getName());
        wxMpMaterial.setFile(file);
        WxMpMaterialUploadResult wxMpMaterialUploadResult = materialService.materialFileUpload(type, wxMpMaterial);
        file.deleteOnExit();
        return Response.success(wxMpMaterialUploadResult);
    }

    /**
     * 新增图片语音素材
     *
     * @return Response
     */
    @AuditRecord("新增视频素材")
    @SneakyThrows
    @PostMapping("/video")
    @PreAuthorize("@authorize.hasPermission('sys_add_material')")
    @ApiOperation(value = "新增视频素材", notes = "新增视频素材")
    public Response saveVideo(@PathVariable String appId, @RequestParam("videoTitle") String videoTitle,
                              @RequestParam("videoIntroduction") String videoIntroduction,
                              @RequestPart("file") MultipartFile multipartFile) {
        WxMpMaterialService materialService = getWxMpMaterialService(appId);
        File file = getFile(multipartFile);
        multipartFile.transferTo(file);
        WxMpMaterial wxMpMaterial = new WxMpMaterial();
        wxMpMaterial.setName(videoTitle);
        wxMpMaterial.setFile(file);
        wxMpMaterial.setVideoTitle(videoTitle);
        wxMpMaterial.setVideoIntroduction(videoIntroduction);
        WxMpMaterialUploadResult wxMpMaterialUploadResult = materialService.materialFileUpload(WxConsts.MaterialType.VIDEO, wxMpMaterial);
        file.deleteOnExit();
        return Response.success(wxMpMaterialUploadResult.getMediaId());
    }

    /**
     * 下载素材(只支持图片,语音)
     *
     * @return ResponseEntity
     */
    @GetMapping("/download/{mediaId}")
    public ResponseEntity<byte[]> download(@PathVariable String appId, @PathVariable String mediaId) throws Exception {
        try {
            WxMpMaterialService wxMpMaterialService = getWxMpMaterialService(appId);
            InputStream is = wxMpMaterialService.materialImageOrVoiceDownload(mediaId);
            byte[] body = new byte[is.available()];
            is.read(body);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attchement;filename=" + URLEncoder.encode(mediaId, "UTF-8"));
            headers.add("Content-Type", "application/octet-stream");
            return new ResponseEntity<>(body, headers, HttpStatus.OK);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 下载素材(只支持视频)
     *
     * @return Response
     */
    @SneakyThrows
    @GetMapping("/download/{mediaId}/video")
    public Response<WxMpMaterialVideoInfoResult> downloadVideo(@PathVariable String appId, @PathVariable String mediaId) {
        WxMpMaterialService wxMpMaterialService = getWxMpMaterialService(appId);
        WxMpMaterialVideoInfoResult wxMpMaterialVideoInfoResult = wxMpMaterialService.materialVideoInfo(mediaId);
        return Response.success(wxMpMaterialVideoInfoResult);
    }

    /**
     * 通过id删除
     *
     * @param mediaIds
     * @return Response
     */
    @AuditRecord("通过id删除")
    @DeleteMapping
    @PreAuthorize("@authorize.hasPermission('sys_delete_material')")
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    public Response delete(@PathVariable String appId, @RequestParam List<String> mediaIds) throws WxErrorException {
        wechatMaterialService.delete(appId, mediaIds);
        return Response.success();
    }

    @NotNull
    private File getFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String[] filename = originalFilename.split("\\.");
        File file = File.createTempFile(filename[0], "." + filename[1]);
        return file;
    }

    private WxMpMaterialService getWxMpMaterialService(String appId) {
        WxMpService wxMpService = WeChatMpCommonService.getWxMpService(appId);
        return wxMpService.getMaterialService();
    }

}
