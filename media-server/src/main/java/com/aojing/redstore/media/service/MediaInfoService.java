package com.aojing.redstore.media.service;

import com.aojing.redstore.media.common.ServerResponse;
import com.aojing.redstore.media.form.MediaForm;
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
     * 新增媒体附件
     * @param mediaForm
     * @return
     */
    public ServerResponse<String> addMediaInfo(MediaForm mediaForm);
    /**
     * 删除文件
     * @param mediaId
     * @param userId
     * @return
     */
    public boolean delete(Integer mediaId, String userId) ;

    /**
     * 上传文件
     * @param fileList
     * @param path
     * @return
     */
    public List<String> upload(List<MultipartFile> fileList, String path);

    public ServerResponse<String> queryById(Integer id, String userId);

    boolean addMediaInfoList(List<MediaOutput> mediaInfoList);

    List<ImgInput> queryImgBygoodsId(List<String> goodsIdList, Integer type);

    /**
     * 查询全部类型
     * @param goodsIdList
     * @return
     */
    List<ImgInput> queryImgBygoodsId(List<String> goodsIdList);
}
