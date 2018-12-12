package com.aojing.redstore.media.service.impl;

import com.aojing.redstore.media.MediaApplicationTests;
import com.aojing.redstore.media.service.MediaInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author gexiao
 * @date 2018/12/5 14:20
 */
public class MediaInfoServiceImplTest extends MediaApplicationTests {

    @Autowired
    MediaInfoService mediaInfoService;

    @Test
    public void upload() {

    }

    @Test
    public void delete() {
      //  mediaInfoService.delete("1.png");
    }

    @Test
    public void addMediaInfo() {
    }
    @Test
    public void queryImgBygoodsId() {

        mediaInfoService.queryImgBygoodsId(Arrays.asList("edc3bf9b504f4c0f9d9f5d33e9d7a88915444273865685goods"),10);
    }
}