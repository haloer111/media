package com.aojing.redstore.media.ctrl;

import com.aojing.redstore.media.common.ServerResponse;
import com.aojing.redstore.media.form.MediaOutput;
import com.aojing.redstore.media.service.MediaInfoService;
import com.aojing.redstore.media.vo.ImgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ServerResponse<List<String>> upload(@RequestParam(value = "uploadFile", required = false) MultipartFile file,
                                         HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);
        ServerResponse<List<String>> targetFileNameList = mediaInfoService.upload(files, path);
        //data = prefix_img + data;
        return targetFileNameList;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ServerResponse<String> delete(Integer mediaId,String userId) {
        return mediaInfoService.delete(mediaId, userId);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ServerResponse<String> query(Integer id,String userId) {
        return mediaInfoService.queryById(id, userId);
    }


    @RequestMapping(value = "/getBgImg", method = RequestMethod.POST)
    public ServerResponse<List<ImgVo>> getBgImg(@RequestBody Map<String,Object>map) {
        List<String> goodsIdList = (List<String>) map.get("goodsIdList");
        Integer type =(Integer)map.get("type");
        return mediaInfoService.queryImgBygoodsId(goodsIdList, type);
    }

    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public ServerResponse<String> addList(@RequestBody List<MediaOutput> mediaInfoList) {
        return mediaInfoService.addMediaInfoList(mediaInfoList);
    }


}
