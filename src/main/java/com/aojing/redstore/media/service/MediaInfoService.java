package com.aojing.redstore.media.service;

import com.aojing.redstore.media.common.ServerResponse;
import com.aojing.redstore.media.form.MediaForm;
import com.aojing.redstore.media.form.MediaOutput;
import com.aojing.redstore.media.vo.ImgVo;
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
    public ServerResponse delete(Integer mediaId, String userId) ;

    /**
     * 上传文件
     * @param file
     * @param path
     * @return
     */
    public ServerResponse<List<String>> upload(List<MultipartFile> fileList, String path);

    public ServerResponse<String> queryById(Integer id, String userId);

    ServerResponse addMediaInfoList(List<MediaOutput> mediaInfoList);

    ServerResponse<List<ImgVo>> queryImgBygoodsId(List<String> goodsIdList, Integer type);
}
