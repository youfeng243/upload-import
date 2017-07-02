package com.haizhi.schedule;

import com.haizhi.mongodb.model.DailyTask;
import com.haizhi.mongodb.service.DailyTaskService;
import com.haizhi.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

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

    private final DailyTaskService dailyTaskService;

    @Autowired
    public ScheduleTask(Environment environment, DailyTaskService dailyTaskService) {

        this.scanPeriod = environment.getProperty("check.period", int.class);
        this.dailyTaskService = dailyTaskService;
        logger.info("扫描周期: {}", this.scanPeriod);
        this.threadNum = environment.getProperty("exec.task.num", int.class);
        logger.info("线程池数目: {}", this.threadNum);
        this.uploadBasePath = environment.getProperty("upload.base.location");
        logger.info("扫描基本目录: {}", this.uploadBasePath);
    }

    @Scheduled(fixedDelay = 1000 * 60 * 30)
    public void scanFolder() {
        logger.info("#####开始扫描文件..");

        for (int i = 0; i < scanPeriod; i++) {

            String date = TimeUtil.getBeforeDate(i);

            //先判断批次任务是否已经上传上来了
            String batchPath = uploadBasePath + date;

            logger.info("当前检测的路径为: {}", batchPath);

            File file = new File(batchPath);

            //判断批次路径是否已经存在 如果不存在则不进行检测
            if (!file.isDirectory() || !file.exists()) {
                logger.info("当前目录不存在: {}", batchPath);
                continue;
            }

            // 获得数据库中批次信息
            DailyTask dailyTask = dailyTaskService.findOne(date);
            if (dailyTask == null) {
                dailyTask = new DailyTask(date);
                logger.info("还未针对当前批次建立任务: {}", date);
            }

            //开始分析每个目录每个文件是否已经被记录

        }

    }

}
