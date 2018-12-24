package com.aojing.redstore.media.common;

import lombok.Data;

import java.util.List;

/**
 * 查询媒体id集合vo
 * @author gexiao
 * @date 2018/12/21 15:58
 */
@Data
public class QueryOutput {
    private List<String> goodsIdList;
    private List<String> sellerIdList;
}
