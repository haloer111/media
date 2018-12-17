package com.aojing.redstore.media.client;

import com.aojing.redstore.media.common.ImgInput;
import com.aojing.redstore.media.common.MediaOutput;
import com.aojing.redstore.media.common.SpringMultipartEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author gexiao
 * @date 2018/12/17 14:30
 */
@FeignClient(name = "media", configuration = SpringMultipartEncoder.class)
public interface MediaClient {

    @PostMapping(value = "/media/getBgImg")
    List<ImgInput> getBgImg(@RequestBody Map<String, Object> map);

    @GetMapping(value = "/media/delete")
    boolean delete(Integer mediaId, String userId);

    @PostMapping(value = "/media/addList")
    boolean addList(@RequestBody List<MediaOutput> mediaInfoList);
   /* @PostMapping(value = "/media/upload")
     List<String> upload(@RequestParam(value = "uploadFile", required = false) MultipartFile file,
                               HttpServletRequest request);*/


    // 存储图片，返回所有图片在fastdfs上的urls
    @RequestMapping(method = RequestMethod.POST, value = "/media/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> upload(@RequestPart(value = "files") MultipartFile[] files);

}
