package com.aojing.redstore.media.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author gexiao
 * @date 2018/12/5 11:22
 */
public interface Const {

    /**
     * 上传类型
     */
    interface UploadType {
        Integer UPLOAD_TYPE_IMG = 1;
        Integer UPLOAD_TYPE_VIDEO = 2;

    }

    /**
     * 媒体类型集合
     */
    interface MediaTypeSet {
        Set<String> IMG_TYPE = Sets.newHashSet("bmp", "jpg", "png", "gif");
        Set<String> VIDEO_TYPE = Sets.newHashSet("mp4", "rmvb", "rm", "avi", "wmv");
    }
    // rm，rmvb，mpeg1-4 mov mtv dat wmv avi 3gp amv dmv flv
    // bmp,jpg,png,tif,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,WMF,webp
}
