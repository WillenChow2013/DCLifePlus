package com.dorun.core.dc.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class FTPTool {

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private int port;


    public FTPClient getFTPClient(String userName, String password) {
        FTPClient ftpClient = null;

        ftpClient = new FTPClient();

        try {
            log.info("---------------------------------------------------------------------");
            log.info("开始连接FTP服务器,host:[{}],,port:[{}]", host, port);
            log.info("---------------------------------------------------------------------");
            ftpClient.connect(host, port);

            log.info("---------------------------------------------------------------------");
            log.info("开始登录FTP服务器,userName:[{}],,password:[{}]", userName, password);
            log.info("---------------------------------------------------------------------");

            ftpClient.login(userName, password);

            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("---------------------------------------------------------------------");
                log.info("登录FTP服务器失败，请检查参数设置");
                log.info("---------------------------------------------------------------------");
            } else {
                log.info("---------------------------------------------------------------------");
                log.info("恭喜！登录FTP服务器成功");
                log.info("---------------------------------------------------------------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return ftpClient;
    }

    public void uploadFIle(String dstPath, String fileName, String targetPath,String userName, String password) {
        FTPClient ftpClient = getFTPClient(userName,password);

        ftpClient.enterLocalPassiveMode();
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            File localFile = new File(dstPath + fileName);

            InputStream is = new FileInputStream(localFile);

            String ftpPath = (StringUtils.isBlank(targetPath) ? "" : targetPath) + "/" + fileName.substring(fileName.lastIndexOf("/") + 1);

            log.info("---------------------------------------------------------------------");
            log.info("文件上传到FTP服务器的路径：[{}]", ftpPath);
            log.info("---------------------------------------------------------------------");

            ftpClient.storeFile(ftpPath, is);

            is.close();
            log.info("---------------------------------------------------------------------");
            log.info("文件上传到FTP服务器成功！");
            log.info("---------------------------------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
                log.info("---------------------------------------------------------------------");
                log.info("ftp连接成功关闭");
                log.info("---------------------------------------------------------------------");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
