package com.aojing.redstore.media.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author gexiao
 * @date 2018/12/12 11:43
 */
@Data
@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
public class ImgVo {

    private String  entityId;
    private String  goodsId;
    private String  menuId;
    private String  hotSellId;
    private String  absolutePath;
}
