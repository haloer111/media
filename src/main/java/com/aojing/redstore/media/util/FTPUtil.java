package com.aojing.redstore.media.util;

import com.aojing.redstore.media.common.Const;
import com.aojing.redstore.media.properties.FtpProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by gexiao
 */
@Slf4j
@EnableConfigurationProperties(FtpProperties.class)
@Component
public class FTPUtil {


    @Value("${ftp.ip}")
    private String ftpIp;
    @Value("${ftp.user}")
    private String ftpUser;
    @Value("${ftp.pass}")
    private String ftpPass;


    public FTPUtil() {
    }

    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    public boolean uploadFile(List<File> fileList, Integer type) throws IOException {
        boolean result = false;
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        log.info("开始连接ftp服务器");
        if (Const.UploadType.UPLOAD_TUPE_IMG == type) {
            result = ftpUtil.uploadFile("img", fileList);
        } else {
            result = ftpUtil.uploadFile("video", fileList);
        }
        log.info("开始连接ftp服务器,结束上传,上传结果:{}");
        return result;
    }

    public boolean delFile(List<String> fileNameList, Integer type) throws IOException {
        boolean result = false;
        //FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        if (connectServer(this.getFtpIp(), 21, this.getFtpUser(), this.getFtpPass())) {

            log.info("开始连接ftp服务器");
            if (Const.UploadType.UPLOAD_TUPE_IMG == type) {
                result = this.delFile("img", fileNameList);
            } else {
                result = this.delFile("video", fileNameList);
            }
            log.info("开始连接ftp服务器,结束上传,上传结果:{}");
            return result;
        }
        return result;
    }


    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;
        //连接FTP服务器
        if (connectServer(this.ip, 21, this.user, this.pwd)) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for (File fileItem : fileList) {
                    fis = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(), fis);
                }

            } catch (IOException e) {
                log.error("上传文件异常", e);
                uploaded = false;
                e.printStackTrace();
            } finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }

    private boolean delFile(String remotePath, List<String> fileNameList) throws IOException {
        boolean deleted = true;
        FileInputStream fis = null;

        //连接FTP服务器
        if (connectServer(ftpIp, 21, ftpUser, ftpPass)) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                for (String fileName : fileNameList) {
                    ftpClient.dele(fileName);
                }
                ftpClient.logout();

            } catch (IOException e) {
                log.error("删除文件异常", e);
                deleted = false;
                e.printStackTrace();
            } finally {
                ftpClient.disconnect();
            }
        }
        return deleted;
    }


    private boolean connectServer(String ip, int port, String user, String pwd) {

        boolean isSuccess = false;
        FTPClient fc = this.getFtpClient();
        try {
            fc.connect(ip);
            isSuccess = fc.login(user, pwd);
        } catch (IOException e) {
            log.error("连接FTP服务器异常", e);
        }
        return isSuccess;
    }


    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient = new FTPClient();

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public String getFtpIp() {
        return ftpIp;
    }

    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpPass() {
        return ftpPass;
    }

    public void setFtpPass(String ftpPass) {
        this.ftpPass = ftpPass;
    }
}
