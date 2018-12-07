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
    IMG(10,"图片"),
    VIDEO(20,"视频"),



    ;

    private int code;
    private String value;
}
