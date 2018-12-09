package com.aojing.redstore.media.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author gexiao
 * @date 2018/12/7 16:42
 */
@Data
public class MediaForm {
    private Integer id;

    private String entityId;

    private String goodsId;

    private String menuId;

    private String hotSellId;

    private String nocount;
    private Integer type;
    private String absolutePath;

    private MultipartFile imgFile;
    private MultipartFile videoFile;
}
