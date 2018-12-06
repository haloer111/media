package com.aojing.redstore.media.ctrl;

import com.aojing.redstore.media.common.ServerResponse;
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

/**
 * @author gexiao
 * @date 2018/12/5 17:52
 */
@RestController
@EnableConfigurationProperties(FtpProperties.class)
public class MediaInfoCtrl {
    @Value("${ftp.prefix_img}")
    private String prefix_img;
    @Value("${ftp.prefix_video}")
    private String prefix_video;


    @Autowired
    private MediaInfoService mediaInfoService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ServerResponse<String> upload(@RequestParam(value = "uploadFile", required = false) MultipartFile file,
                                         HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        ServerResponse<String> targetFileName = mediaInfoService.upload(file, path);
        String data = targetFileName.getData();
        data = data + prefix_img;
        return ServerResponse.createBySuccess(data);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ServerResponse<String> delete(String fileName) {
        return mediaInfoService.delete(fileName);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ServerResponse<String> query(Integer id) {
        return mediaInfoService.queryById(id);
    }


}
