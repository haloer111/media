package com.aojing.redstore.media.dao;

import com.aojing.redstore.media.pojo.MediaInfo;
import org.apache.ibatis.annotations.Param;

public interface MediaInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MediaInfo record);

    int insertSelective(MediaInfo record);

    MediaInfo selectByPrimaryKey(Integer id);
    MediaInfo selectByIdAndUserId(@Param("id") Integer id,@Param("userId") String userId);

    int updateByPrimaryKeySelective(MediaInfo record);

    int updateByPrimaryKey(MediaInfo record);
}