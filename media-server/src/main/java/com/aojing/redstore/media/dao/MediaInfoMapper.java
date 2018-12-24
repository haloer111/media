package com.aojing.redstore.media.dao;

import com.aojing.redstore.media.pojo.MediaInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MediaInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(MediaInfo record);

    int insertSelective(MediaInfo record);

    MediaInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MediaInfo record);

    int updateByPrimaryKey(MediaInfo record);

    //manual
    List<MediaInfo> queryImgBygoodsIdAndType(@Param("goodsId") String goodsId, @Param("type") Integer type);
    List<MediaInfo> queryImgBygoodsId(@Param("goodsId") String goodsId);

    String selectByUserId(@Param("userid")String userid,@Param("code") int code);

    List<MediaInfo> queryImgBySellerId(@Param("sellerId")String sellerId);
}