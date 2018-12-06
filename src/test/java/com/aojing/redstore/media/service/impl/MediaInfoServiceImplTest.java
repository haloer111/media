package com.aojing.redstore.media.service.impl;

import com.aojing.redstore.media.MediaApplicationTests;
import com.aojing.redstore.media.service.MediaInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        mediaInfoService.delete("1.png");
    }

    @Test
    public void addMediaInfo() {
    }
}