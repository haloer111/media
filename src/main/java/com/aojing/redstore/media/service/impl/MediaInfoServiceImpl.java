package com.aojing.redstore.media.service.impl;

import java.util.ArrayList;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.aojing.redstore.media.common.Const;
import com.aojing.redstore.media.common.ServerResponse;
import com.aojing.redstore.media.dao.MediaInfoMapper;
import com.aojing.redstore.media.enums.ExceptionEnum;
import com.aojing.redstore.media.enums.UploadTypeEnum;
import com.aojing.redstore.media.exception.RedStoreException;
import com.aojing.redstore.media.pojo.MediaInfo;
import com.aojing.redstore.media.properties.FtpProperties;
import com.aojing.redstore.media.service.MediaInfoService;
import com.aojing.redstore.media.util.FTPUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
public class MediaInfoServiceImpl implements MediaInfoService {


    @Autowired
    private MediaInfoMapper mapper;
    @Autowired
    FTPUtil fTPUtil;

    public ServerResponse<String> upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        Integer type = null;
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
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
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
            return null;
        }
        //A:abc.jpg
        //B:abc.jpg
        return ServerResponse.createBySuccess(targetFile.getName());
    }


    public ServerResponse delete(String fileName) {
        if (fileName == null) {
            return ServerResponse.createByErrorMessage("文件名不能为空");
        }
        Integer type = null;
        List<String> fileNameList = new ArrayList<>();
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

    public ServerResponse<String> addMediaInfo(MediaInfo mediaInfo) {
        int result = mapper.insertSelective(mediaInfo);
        if (result > 0) {
            return ServerResponse.createBySuccess("新增媒体附件成功");
        }
        return ServerResponse.createByErrorMessage("新增媒体附件失败");

    }
    public ServerResponse<String> queryById(Integer id) {
        MediaInfo mediaInfo = mapper.selectByPrimaryKey(id);
        if (mediaInfo != null) {
            return ServerResponse.createBySuccess("新增媒体附件成功",mediaInfo.getName());
        }
        return ServerResponse.createByErrorMessage("新增媒体附件失败");

    }
}
