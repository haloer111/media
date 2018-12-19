package com.aojing.redstore.media.service.impl;

import com.aojing.redstore.media.MediaServerApplicationTests;
import com.aojing.redstore.media.common.ImgInput;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author gexiao
 * @date 2018/12/18 16:56
 */
public class MediaInfoServiceImplTest extends MediaServerApplicationTests {

    @Autowired
    private MediaInfoServiceImpl mediaInfoService;
    @Test
    public void queryImgBygoodsId() {
        List<String> list = new ArrayList<>();
        list.add("edc3bf9b504f4c0f9d9f5d33e9d7a88915444273865685goods");
        list.add("hgrt4563343f9d9f5d33e9d7a88915444273865685goods");
        List<ImgInput> imgInputs = mediaInfoService.queryImgBygoodsId(list);
        Assert.assertNotNull(imgInputs);
    }
    @Test
    public void queryImgBygoodsId1() {
        List<String> list = new ArrayList<>();
        list.add("edc3bf9b504f4c0f9d9f5d33e9d7a88915444273865685goods");
        list.add("hgrt4563343f9d9f5d33e9d7a88915444273865685goods");
        List<ImgInput> imgInputs = mediaInfoService.queryImgBygoodsId(list,10);
        Assert.assertNotNull(imgInputs);
    }
}