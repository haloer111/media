package com.aojing.redstore.media.service;

import com.aojing.redstore.media.common.QueryOutput;
import com.aojing.redstore.media.common.ServerResponse;
import com.aojing.redstore.media.common.MediaOutput;
import com.aojing.redstore.media.common.ImgInput;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author gexiao
 * @date 2018/12/5 10:56
 */
public interface MediaInfoService {
    /**
     * 删除文件
     * @param mediaId
     * @param userId
     * @return
     */
    public boolean delete(String mediaId, String userId) ;

    /**
     * 上传文件
     * @param fileList
     * @param path
     * @return
     */
    public List<String> upload(List<MultipartFile> fileList, String path);

    public ServerResponse<String> queryById(String id, String userId);

    boolean addMediaInfoList(List<MediaOutput> mediaInfoList);

    List<ImgInput> queryImgByQueryOutput(QueryOutput queryOutput, Integer type) ;

    /**
     * 查询全部类型
     * @param goodsIdList
     * @return
     */
    List<ImgInput> queryImgByQueryOutput(QueryOutput queryOutput);

    /**
     * 查询用户的头像
     * @param userIdIdList 用户id集合
     * @return
     */
     List<ImgInput> queryIconByUserId(List<String> userIdIdList);
}
