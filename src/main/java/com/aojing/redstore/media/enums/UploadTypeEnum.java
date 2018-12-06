package com.aojing.redstore.media.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gexiao
 * @date 2018/12/5 11:42
 */
@Getter
@AllArgsConstructor
public enum UploadTypeEnum {
    IMG(1,"img"),
    VIDEO(2,"mp4"),



    ;

    private int code;
    private String value;



}
