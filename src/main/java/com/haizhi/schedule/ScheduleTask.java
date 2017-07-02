package com.haizhi.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by youfeng on 2017/7/2.
 * 周期任务
 */
@Component
public class ScheduleTask {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 扫描周期
    private int scanPeriod = 10;

    //线程数目
    private int threadNum = 10;

    //上传基本根路径
    private String uploadBasePath;

    @Autowired
    public ScheduleTask(Environment environment) {

        this.scanPeriod = environment.getProperty("check.period", int.class);
        logger.info("扫描周期: {}", this.scanPeriod);
        this.threadNum = environment.getProperty("exec.task.num", int.class);
        logger.info("线程池数目: {}", this.threadNum);
        this.uploadBasePath = environment.getProperty("upload.base.location");
        logger.info("扫描基本目录: {}", this.uploadBasePath);
    }

    @Scheduled(fixedDelay = 1000 * 60 * 30)
    public void scanFolder() {
        logger.info("#####开始扫描文件..");
    }

}
