package com.aojing.redstore.media.form;

import lombok.Data;

@Data
public class MediaOutput {
    private Integer id;

    private String entityId;

    private String goodsId;

    private String menuId;

    private String hotSellId;

    private String nocount;
    private Integer type;
    private String absolutePath;


    public MediaOutput() {
    }

    public MediaOutput(Integer id, String entityId, String goodsId, String menuId, String hotSellId, String nocount,
                       Integer type, String absolutePath) {
        this.id = id;
        this.entityId = entityId;
        this.goodsId = goodsId;
        this.menuId = menuId;
        this.hotSellId = hotSellId;
        this.nocount = nocount;
        this.type = type;
        this.absolutePath = absolutePath;
    }
}