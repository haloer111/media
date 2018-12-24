package com.aojing.redstore.media.service.impl;

import com.aojing.redstore.media.common.*;
import com.aojing.redstore.media.dao.MediaInfoMapper;
import com.aojing.redstore.media.enums.ExceptionEnum;
import com.aojing.redstore.media.enums.FileTypeEnum;
import com.aojing.redstore.media.exception.RedStoreException;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

    public List<String> upload(List<MultipartFile> fileList, String path) {
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
                return null;
            }

        }

        return uploadFileNameList;
    }


    public boolean delete(String mediaId, String userId) {
        if (mediaId == null) {
            return false;
        }
        Integer type = null;
        List<String> fileNameList = new ArrayList<>();

        //1. 通过id查询出文件名
        String fileName = this.queryById(mediaId, userId).getData();
        if (StringUtils.isBlank(fileName)) {
            return false;
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
            return false;
        }

        int result = mapper.deleteByPrimaryKey(mediaId);
        if (result > 0) {
            return true;
        }
        return false;
    }


    public ServerResponse<String> queryById(String id, String userId) {
        MediaInfo mediaInfo = mapper.selectByPrimaryKey(id);
        if (mediaInfo != null) {
            return ServerResponse.createBySuccess("新增媒体附件成功", mediaInfo.getName());
        }
        return ServerResponse.createByErrorMessage("新增媒体附件失败");

    }

    @Override
    public boolean addMediaInfoList(List<MediaOutput> mediaInfoList) {
        for (MediaOutput mediaForm : mediaInfoList) {
            MediaInfo mediaInfo = new MediaInfo();
            BeanUtils.copyProperties(mediaForm, mediaInfo);
            //设置名字
            //mediaForm.getAbsolutePath().substring()
            //mediaInfo.setName("");
            //todo 修改表结构,赋值id 12.20
            mediaInfo.setNocount("");
            mediaInfo.setLllustrate("");
            mediaInfo.setLeadingOfficical("");
            mediaInfo.setLocation("");
            mediaInfo.setRemark("");
            mediaInfo.setCreateTime(new Date());
            mediaInfo.setUpdateTime(new Date());
            int result = mapper.insertSelective(mediaInfo);
            if (result <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<ImgInput> queryImgByQueryOutput(QueryOutput queryOutput, Integer type) {
        List<String> goodsIdList = queryOutput.getGoodsIdList();
        List<String> sellerIdList = queryOutput.getSellerIdList();
        List<ImgInput> imgInputList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(goodsIdList)) {
            for (String goodsId : goodsIdList) {
                List<MediaInfo> mediaInfoList = mapper.queryImgBygoodsIdAndType(goodsId, type);
                if (!CollectionUtils.isEmpty(mediaInfoList)) {
                    assembleImgInput(imgInputList, mediaInfoList);
                }
            }
        }
        if (!CollectionUtils.isEmpty(sellerIdList)) {
            for (String sellerId : sellerIdList) {
                List<MediaInfo> mediaInfoList = mapper.queryImgBySellerId(sellerId);
                assembleImgInput(imgInputList, mediaInfoList);
            }
        }

        return imgInputList;

    }

    @Override
    public List<ImgInput> queryImgByQueryOutput(QueryOutput queryOutput) {
        List<String> goodsIdList = queryOutput.getGoodsIdList();
        List<String> sellerIdList = queryOutput.getSellerIdList();
        List<ImgInput> imgInputList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(goodsIdList)) {
            for (String goodsId : goodsIdList) {
                List<MediaInfo> mediaInfoList = mapper.queryImgBygoodsId(goodsId);
                assembleImgInput(imgInputList, mediaInfoList);
            }
        }
        if (!CollectionUtils.isEmpty(sellerIdList)) {
            for (String sellerId : sellerIdList) {
                List<MediaInfo> mediaInfoList = mapper.queryImgBySellerId(sellerId);
                if (mediaInfoList.size() > 0) {
                    assembleImgInput(imgInputList, mediaInfoList);
                }
            }
        }
        //单独处理
        return imgInputList;
    }

    private List<ImgInput> assembleImgInput(List<ImgInput> imgInputList, List<MediaInfo> mediaInfoList) {
        ImgInput imgInput = new ImgInput();
        BeanUtils.copyProperties(mediaInfoList.get(0), imgInput);
        List<String> productImg = new ArrayList<>();
        List<String> detailImg = new ArrayList<>();
        List<String> slightlyThumbnail = new ArrayList<>();
        List<String> bgVideo = new ArrayList<>();
        List<String> productVideo = new ArrayList<>();
        for (MediaInfo mediaInfo : mediaInfoList) {
            if (mediaInfo.getType() == FileTypeEnum.BG_IMG.getCode()) {
                imgInput.setBgImg(mediaInfo.getAbsolutePath());
            } else if (mediaInfo.getType() == FileTypeEnum.ICON.getCode()) {
                imgInput.setIcon(mediaInfo.getAbsolutePath());
            } else if (mediaInfo.getType() == FileTypeEnum.PRODUCT_IMG.getCode()) {
                productImg.add(mediaInfo.getAbsolutePath());
                imgInput.setProductImg(productImg);
            } else if (mediaInfo.getType() == FileTypeEnum.DETAIL_IMG.getCode()) {
                detailImg.add(mediaInfo.getAbsolutePath());
                imgInput.setDetailImg(detailImg);
            } else if (mediaInfo.getType() == FileTypeEnum.SLIGHTLY_THUMBNAIL.getCode()) {
                slightlyThumbnail.add(mediaInfo.getAbsolutePath());
                imgInput.setSlightlyThumbnail(slightlyThumbnail);
            } else if (mediaInfo.getType() == FileTypeEnum.BG_VIDEO.getCode()) {
                bgVideo.add(mediaInfo.getAbsolutePath());
                imgInput.setBgVideo(bgVideo);
            } else if (mediaInfo.getType() == FileTypeEnum.PRODUCT_VIDEO.getCode()) {
                productVideo.add(mediaInfo.getAbsolutePath());
                imgInput.setProductVideo(productVideo);
            }
        }
        imgInputList.add(imgInput);
        return imgInputList;
    }


    public List<ImgInput> queryIconByUserId(List<String> userIdIdList) {
        List<ImgInput> imgInputList = new ArrayList<>();
        for (String userid : userIdIdList) {
            ImgInput imgInput = new ImgInput();
            String icon = mapper.selectByUserId(userid, FileTypeEnum.ICON.getCode());
            if (StringUtils.isNotBlank(icon)) {
                imgInput.setIcon(icon);
            }
            imgInput.setEntityId(userid);
            imgInputList.add(imgInput);
        }
        return imgInputList;
    }
}
