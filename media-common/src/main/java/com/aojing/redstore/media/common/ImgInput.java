package com.aojing.redstore.media.common;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

/**
 * @author gexiao
 * @date 2018/12/12 11:43
 */
@Data
@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
public class ImgInput {

    private String  entityId;
    private String  goodsId;
    private String  menuId;
    private String  hotSellId;
  //  private String  absolutePath;

    /**
     * 卖家头像
     */
    String icon;
    /**
     * 背景图片
     */
    String bgImg;
    /**
     * 产品图
     */
   List<String> productImg;
    /**
     * 详细图
     */
    List<String> detailImg;
    /**
     * 略缩图
     */
    List<String> slightlyThumbnail;

    /**
     * 主页背景视频
     */
    List<String> bgVideo;

    /**
     * 产品视频
     */
    List<String> productVideo;
}
