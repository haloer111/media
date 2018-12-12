package com.aojing.redstore.media.service.impl;

import java.util.*;

import com.aojing.redstore.media.common.Const;
import com.aojing.redstore.media.common.ServerResponse;
import com.aojing.redstore.media.dao.MediaInfoMapper;
import com.aojing.redstore.media.enums.ExceptionEnum;
import com.aojing.redstore.media.exception.RedStoreException;
import com.aojing.redstore.media.form.MediaForm;
import com.aojing.redstore.media.form.MediaOutput;
import com.aojing.redstore.media.pojo.MediaInfo;
import com.aojing.redstore.media.properties.FtpProperties;
import com.aojing.redstore.media.service.MediaInfoService;
import com.aojing.redstore.media.util.FTPUtil;
import com.aojing.redstore.media.vo.ImgVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

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

    public ServerResponse<List<String>> upload(List<MultipartFile> fileList, String path) {
        String absUploadFileName = null;
        String uploadFileName = null;
        List<String> uploadFileNameList = new ArrayList<>();
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
                    type = Const.UploadType.UPLOAD_TYPE_IMG;
                    absUploadFileName = prefix_img + uploadFileName;
                }
                if (Const.MediaTypeSet.VIDEO_TYPE.contains(fileExtensionName)) {
                    type = Const.UploadType.UPLOAD_TYPE_VIDEO;
                    absUploadFileName = prefix_video + uploadFileName;

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
                //如果上传成功,加到返回文件名字列表
                uploadFileNameList.add(absUploadFileName);
                targetFile.delete();
            } catch (IOException e) {
                log.error("上传文件异常", e);
                return ServerResponse.createByErrorMessage("上传失败");
            }

        }

        return ServerResponse.createBySuccess(uploadFileNameList);
    }


    public ServerResponse delete(Integer mediaId, String userId) {
        if (mediaId == null) {
            return ServerResponse.createByErrorMessage("文件id不能为空");
        }
        Integer type = null;
        List<String> fileNameList = new ArrayList<>();

        //1. 通过id查询出文件名
        String fileName = this.queryById(mediaId, userId).getData();
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
                type = Const.UploadType.UPLOAD_TYPE_IMG;
            }
            if (Const.MediaTypeSet.VIDEO_TYPE.contains(fileExtensionName)) {
                type = Const.UploadType.UPLOAD_TYPE_VIDEO;
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

        int result = mapper.deleteByPrimaryKey(mediaId);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByError();
    }

    public ServerResponse<String> addMediaInfo(MediaForm mediaForm) {
        /*MediaInfo mediaInfo = new MediaInfo();
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
        }*/
        return ServerResponse.createByErrorMessage("新增媒体附件失败");

    }

    public ServerResponse<String> queryById(Integer id, String userId) {
        MediaInfo mediaInfo = mapper.selectByPrimaryKey(id);
        if (mediaInfo != null) {
            return ServerResponse.createBySuccess("新增媒体附件成功", mediaInfo.getName());
        }
        return ServerResponse.createByErrorMessage("新增媒体附件失败");

    }

    @Override
    public ServerResponse addMediaInfoList(List<MediaOutput> mediaInfoList) {
        for (MediaOutput mediaForm : mediaInfoList) {
            MediaInfo mediaInfo = new MediaInfo();
            BeanUtils.copyProperties(mediaForm, mediaInfo);
            //设置名字
            //mediaForm.getAbsolutePath().substring()
            //mediaInfo.setName("");

            mediaInfo.setNocount("");
            mediaInfo.setLllustrate("");
            mediaInfo.setLeadingOfficical("");
            mediaInfo.setLocation("");
            mediaInfo.setRemark("");
            mediaInfo.setCreateTime(new Date());
            mediaInfo.setUpdateTime(new Date());
            int result = mapper.insertSelective(mediaInfo);
            if (result <= 0) {
                return ServerResponse.createByErrorMessage("新增媒体附件失败");
            }
        }
        return ServerResponse.createBySuccess("新增媒体附件成功");
    }

    @Override
    public ServerResponse<List<ImgVo>> queryImgBygoodsId(List<String> goodsIdList, Integer type) {
        if (null == type || CollectionUtils.isEmpty(goodsIdList)) {
            return ServerResponse.createByErrorMessage("传入参数失败");
        }
        List<ImgVo> imgVoList = new ArrayList<>();
        for (String goodsId : goodsIdList) {
            List<MediaInfo> mediaInfoList = mapper.queryImgBygoodsIdAndType(goodsId, type);
            for (MediaInfo mediaInfo : mediaInfoList) {
                ImgVo imgVo = new ImgVo();
                BeanUtils.copyProperties(mediaInfo, imgVo);
                imgVoList.add(imgVo);
            }
            //测试
             //imgVoList.addAll(mediaInfoList.stream().map(e -> new ImgVo()).collect(Collectors.toList()));
        }
        return ServerResponse.createBySuccess(imgVoList);

    }

}
