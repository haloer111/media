package com.aojing.redstore.media.dao;

import com.aojing.redstore.media.pojo.MediaInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MediaInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MediaInfo record);

    int insertSelective(MediaInfo record);

    MediaInfo selectByPrimaryKey(Integer id);
    MediaInfo selectByIdAndUserId(@Param("id") Integer id, @Param("userId") String userId);

    int updateByPrimaryKeySelective(MediaInfo record);

    int updateByPrimaryKey(MediaInfo record);




    //manual
    List<MediaInfo> queryImgBygoodsIdAndType(@Param("goodsId") String goodsId, @Param("type") Integer type);
}