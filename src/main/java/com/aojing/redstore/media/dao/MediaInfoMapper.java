package com.aojing.redstore.media.dao;

import com.aojing.redstore.media.pojo.MediaInfo;

public interface MediaInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MediaInfo record);

    int insertSelective(MediaInfo record);

    MediaInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MediaInfo record);

    int updateByPrimaryKey(MediaInfo record);
}