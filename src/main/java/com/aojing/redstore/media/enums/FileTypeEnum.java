package com.aojing.redstore.media.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gexiao
 * @date 2018/12/7 16:53
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum {
    /**
     * 背景图片
     */
    BG_IMG(10,"背景图片"),
    /**
     * 头像图标
     */
    ICON(20,"头像图标"),

    /**
     * 产品图
     */
    PRODUCT_IMG(30,"产品图"),
    /**
     * 详细图
     */
    DETAIL_IMG(40,"详细图"),
    /**
     * 略缩图
     */
    SLIGHTLY_THUMBNAIL(50,"略缩图"),


    /**
     * 主页背景视频
     */
    BG_VIDEO(60,"主页背景视频"),

    /**
     * 产品视频
     */
    PRODUCT_VIDEO(70,"产品视频"),


    ;

    private int code;
    private String value;
}
