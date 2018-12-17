package com.aojing.redstore.media.form;

import lombok.Data;

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

}
