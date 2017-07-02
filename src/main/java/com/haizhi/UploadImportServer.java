package com.haizhi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UploadImportServer {

    private static Logger logger = LoggerFactory.getLogger(UploadImportServer.class);

    public static void main(String[] args) {
        logger.info("启动上传导入数据程序...");
        SpringApplication.run(UploadImportServer.class, args);
    }
}
