package com.aojing.redstore.media.ctrl;

import com.aojing.redstore.media.common.ServerResponse;
import com.aojing.redstore.media.common.MediaOutput;
import com.aojing.redstore.media.service.MediaInfoService;
import com.aojing.redstore.media.common.ImgInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author gexiao
 * @date 2018/12/5 17:52
 */
@RestController
@RequestMapping("/media")
public class MediaInfoCtrl {


    @Autowired
    private MediaInfoService mediaInfoService;

   /* @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public List<String> upload(@RequestParam(value = "uploadFile", required = false) MultipartFile file,
                                         HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);
        List<String> targetFileNameList = mediaInfoService.upload(files, path);
        //data = prefix_img + data;
        return targetFileNameList;
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "/media/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> upload(@RequestPart(value = "files") MultipartFile[] files,

                        HttpServletRequest request) {
        List<MultipartFile> multipartFiles = ((MultipartHttpServletRequest) request).getFiles("multipartFiles");
        String path = request.getSession().getServletContext().getRealPath("upload");
        List<String> targetFileNameList = mediaInfoService.upload(multipartFiles, path);
        return targetFileNameList;

    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public boolean delete(Integer mediaId, String userId) {
        return mediaInfoService.delete(mediaId, userId);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ServerResponse<String> query(Integer id, String userId) {
        return mediaInfoService.queryById(id, userId);
    }


    @RequestMapping(value = "/getBgImg", method = RequestMethod.POST)
    public List<ImgInput> getBgImg(@RequestBody Map<String, Object> map) {
        List<String> goodsIdList = (List<String>) map.get("goodsIdList");
        Integer type = (Integer) map.get("type");
        return mediaInfoService.queryImgBygoodsId(goodsIdList, type);
    }

    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public boolean addList(@RequestBody List<MediaOutput> mediaInfoList) {
        return mediaInfoService.addMediaInfoList(mediaInfoList);
    }


}