package com.aojing.redstore.media.service;

import com.aojing.redstore.media.common.ServerResponse;
import com.aojing.redstore.media.pojo.MediaInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author gexiao
 * @date 2018/12/5 10:56
 */
public interface MediaInfoService {
    /**
     * 新增媒体附件
     * @param mediaInfo
     * @return
     */
    public ServerResponse<String> addMediaInfo(MediaInfo mediaInfo);

    /**
     * 删除文件
     * @param fileName
     * @return
     */
    public ServerResponse delete(String fileName) ;

    /**
     * 上传文件
     * @param file
     * @param path
     * @return
     */
    public ServerResponse<String> upload(MultipartFile file, String path);

    public ServerResponse<String> queryById(Integer id);

}
