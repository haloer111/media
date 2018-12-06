package com.aojing.redstore.media.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author gexiao
 * @date 2018/12/4 17:32
 */
@Configuration
@ConfigurationProperties(prefix = "ftp",ignoreUnknownFields = false)
@Data
@PropertySource("classpath:config/goods-common.properties")
public class FtpProperties {

   private String ip;
   private String user;
   private String pass;
   private String prefix_img;
   private String prefix_video;
}
