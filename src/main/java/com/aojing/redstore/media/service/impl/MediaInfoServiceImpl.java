package com.aojing.redstore.media.service.impl;

import java.util.Date;

import java.util.ArrayList;

import com.aojing.redstore.media.common.Const;
import com.aojing.redstore.media.common.ServerResponse;
import com.aojing.redstore.media.dao.MediaInfoMapper;
import com.aojing.redstore.media.enums.ExceptionEnum;
import com.aojing.redstore.media.enums.FileTypeEnum;
import com.aojing.redstore.media.exception.RedStoreException;
import com.aojing.redstore.media.form.MediaForm;
import com.aojing.redstore.media.pojo.MediaInfo;
import com.aojing.redstore.media.properties.FtpProperties;
import com.aojing.redstore.media.service.MediaInfoService;
import com.aojing.redstore.media.util.FTPUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author gexiao
 * @date 2018/12/5 10:58
 */
@Service
@Slf4j
@EnableConfigurationProperties(FtpProperties.class)
public class MediaInfoServiceImpl implements MediaInfoService {
    @Value("${ftp.prefix_img}")
    private String prefix_img;
    @Value("${ftp.prefix_video}")
    private String prefix_video;


    @Autowired
    private MediaInfoMapper mapper;
    @Autowired
    FTPUtil fTPUtil;

    public ServerResponse<String> upload(List<MultipartFile> fileList, String path) {
        String absUploadFileName = null;
        String uploadFileName = null;
        for (MultipartFile file : fileList) {

            String fileName = file.getOriginalFilename();
            Integer type = null;
            //扩展名
            //abc.jpg
            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (StringUtils.isNotBlank(fileExtensionName)) {
                uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
                //判断上传的类型
                if (Const.MediaTypeSet.IMG_TYPE.contains(fileExtensionName)) {
                    type = Const.UploadType.UPLOAD_TUPE_IMG;
                    // absUploadFileName = prefix_img + uploadFileName;
                }
                if (Const.MediaTypeSet.VIDEO_TYPE.contains(fileExtensionName)) {
                    type = Const.UploadType.UPLOAD_TUPE_VIDEO;
                    //  absUploadFileName = prefix_video + uploadFileName;

                }
            } else {
                throw new RedStoreException(ExceptionEnum.PARAM_ERROR);
            }
            log.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

            File fileDir = new File(path);
            if (!fileDir.exists()) {
                fileDir.setWritable(true);
                fileDir.mkdirs();
            }
            File targetFile = new File(path, uploadFileName);

            try {
                file.transferTo(targetFile);
                //文件已经上传成功了


                fTPUtil.uploadFile(Lists.newArrayList(targetFile), type);
                //已经上传到ftp服务器上

                targetFile.delete();
            } catch (IOException e) {
                log.error("上传文件异常", e);
                return ServerResponse.createByErrorMessage("上传失败");
            }

        }

        return ServerResponse.createBySuccess(uploadFileName);
    }


    public ServerResponse delete(Integer mediaId) {
        if (mediaId == null) {
            return ServerResponse.createByErrorMessage("文件id不能为空");
        }
        Integer type = null;
        List<String> fileNameList = new ArrayList<>();

        //1. 通过id查询出文件名
        String fileName = this.queryById(mediaId).getData();
        if (StringUtils.isBlank(fileName)) {
            return ServerResponse.createByErrorMessage("未能查询到文件名");
        }

        fileNameList.add(fileName);
        //扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (StringUtils.isNotBlank(fileExtensionName)) {
            //判断上传的类型
            if (Const.MediaTypeSet.IMG_TYPE.contains(fileExtensionName)) {
                type = Const.UploadType.UPLOAD_TUPE_IMG;
            }
            if (Const.MediaTypeSet.VIDEO_TYPE.contains(fileExtensionName)) {
                type = Const.UploadType.UPLOAD_TUPE_VIDEO;
            }
        } else {
            throw new RedStoreException(ExceptionEnum.PARAM_ERROR);
        }
        try {

            //System.out.println(JSON.toJSONString(fTPUtil));
            fTPUtil.delFile(fileNameList, type);
            //已经删除ftp服务器上

        } catch (IOException e) {
            log.error("上传文件异常", e);
            return ServerResponse.createByError();
        }
        return ServerResponse.createBySuccess();
    }

    public ServerResponse<String> addMediaInfo(MediaForm mediaForm) {
        MediaInfo mediaInfo = new MediaInfo();
        BeanUtils.copyProperties(mediaForm, mediaInfo);
        List<MultipartFile> fileList = new ArrayList<>();

        if (mediaInfo.getType() == FileTypeEnum.IMG.getCode()) {
            //调用上传方法
            fileList.add(mediaForm.getImgFile());
            ServerResponse<String> upload = this.upload(fileList, "upload");
            if (!upload.isSuccess()) {
                throw new RedStoreException(ExceptionEnum.UPLOAD_FAIL);
            }

            mediaInfo.setAbsolutePath(prefix_img + upload.getData());
            mediaInfo.setRelativePath(upload.getData());
        } else if (mediaInfo.getType() == FileTypeEnum.VIDEO.getCode()) {
            fileList.add(mediaForm.getImgFile());
            ServerResponse<String> upload = this.upload(fileList, "upload");
            if (!upload.isSuccess()) {
                throw new RedStoreException(ExceptionEnum.UPLOAD_FAIL);
            }
            mediaInfo.setAbsolutePath(prefix_video + upload.getData());
            mediaInfo.setRelativePath(upload.getData());
        }
        mediaInfo.setNocount("");
        mediaInfo.setLllustrate("");
        mediaInfo.setLeadingOfficical("");
        mediaInfo.setLocation("");
        mediaInfo.setRemark("");
        mediaInfo.setCreateTime(new Date());
        mediaInfo.setUpdateTime(new Date());

        int result = mapper.insertSelective(mediaInfo);
        if (result > 0) {
            return ServerResponse.createBySuccess("新增媒体附件成功");
        }
        return ServerResponse.createByErrorMessage("新增媒体附件失败");

    }

    public ServerResponse<String> queryById(Integer id) {
        MediaInfo mediaInfo = mapper.selectByPrimaryKey(id);
        if (mediaInfo != null) {
            return ServerResponse.createBySuccess("新增媒体附件成功", mediaInfo.getName());
        }
        return ServerResponse.createByErrorMessage("新增媒体附件失败");

    }
}
