package com.aojing.redstore.media.ctrl;

import com.aojing.redstore.media.common.ServerResponse;
import com.aojing.redstore.media.form.MediaForm;
import com.aojing.redstore.media.pojo.MediaInfo;
import com.aojing.redstore.media.properties.FtpProperties;
import com.aojing.redstore.media.service.MediaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gexiao
 * @date 2018/12/5 17:52
 */
@RestController
@RequestMapping("/media")
public class MediaInfoCtrl {


    @Autowired
    private MediaInfoService mediaInfoService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ServerResponse<String> upload(@RequestParam(value = "uploadFile", required = false) MultipartFile file,
                                         HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);
        ServerResponse<String> targetFileName = mediaInfoService.upload(files, path);
        String data = targetFileName.getData();
        //data = prefix_img + data;
        return ServerResponse.createBySuccess(data);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ServerResponse<String> delete(Integer mediaId) {
        return mediaInfoService.delete(mediaId);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ServerResponse<String> query(Integer id) {
        return mediaInfoService.queryById(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ServerResponse<String> add(MediaForm mediaInfo) {
        return mediaInfoService.addMediaInfo(mediaInfo);
    }


}
