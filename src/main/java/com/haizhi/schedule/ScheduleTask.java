package com.haizhi.schedule;

import com.haizhi.engine.RunTask;
import com.haizhi.mongodb.model.DailyTask;
import com.haizhi.mongodb.model.FileDetail;
import com.haizhi.mongodb.model.FolderDetail;
import com.haizhi.mongodb.service.DailyTaskService;
import com.haizhi.type.ImportType;
import com.haizhi.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    //最大待处理线程数目
    private static final int MAX_THREAD_POOL = 50;

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

    //导入任务
    private void importTask(List<DailyTask> dailyTaskList) {
        logger.info("开始执行导入任务...");

        dailyTaskList.forEach(dailyTask -> {
            Map<String, FolderDetail> folderList = dailyTask.getFolderList();

            //获得当前日期路径
            String batchPath = uploadBasePath + dailyTask.getDate() + "/";

            // 这里创建线程池
            ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);
            for (Map.Entry<String, FolderDetail> entry : folderList.entrySet()) {
                // 当前导入类型数据文件夹路径
                String folderPath = batchPath + entry.getKey() + "/";
                threadPool.submit(new RunTask(folderPath, entry.getValue()));
            }
            threadPool.shutdown();
            logger.info("导出数据线程池加载结束, 等待线程运行结束...");
            try {
                threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                logger.error("线程池被中断...", e);
            }

            //存储当前批次导入信息
            dailyTaskService.saveOne(dailyTask);

            logger.info("导出数据线程池等待线程运行结束...");
        });

        logger.info("导入任务执行完成...");
    }

    @Scheduled(fixedDelay = 1000 * 60 * 30)
    public void scanTask() {

        // 扫描目录
        List<DailyTask> dailyTaskList = scanFolder();


        // 执行任务
        importTask(dailyTaskList);
    }

    private List<DailyTask> scanFolder() {
        logger.info("#####开始扫描文件..");

        List<DailyTask> dailyTaskList = new ArrayList<>();
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
            Map<String, FolderDetail> folderDetailMap = dailyTask.getFolderList();
            for (ImportType importType : ImportType.values()) {

                String folderName = importType.getName();
                // 获得不用类型数据导入目录完整路径
                String fullImportFolderPath = batchPath + "/" + folderName;

                //判断路径是否存在
                File importFileFolder = new File(fullImportFolderPath);
                if (!importFileFolder.isDirectory() || !importFileFolder.exists()) {
                    logger.info("当前目录不存在: {}", fullImportFolderPath);
                    continue;
                }

                //获得目录详细信息
                FolderDetail folderDetail = folderDetailMap.computeIfAbsent(folderName, FolderDetail::new);
                Map<String, FileDetail> fileDetailMap = folderDetail.getFileList();

                //遍历目录
                Path importFolderPath = Paths.get(fullImportFolderPath);

                List<String> folderList;
                try {
                    folderList = Files.walk(importFolderPath)
                            .filter(path -> !path.equals(importFolderPath) && !Files.isDirectory(path))
                            .map(importFolderPath::relativize).map(Path::toString).collect(Collectors.toList());
                } catch (IOException e) {
                    folderList = null;
                    logger.error("遍历目录失败:", e);
                }

                //如果遍历失败了 则不进行下一步分析处理
                if (folderList == null) {
                    continue;
                }

                folderList.forEach(fileName -> {
                    if (fileDetailMap.containsKey(fileName)) {
                        return;
                    }
                    FileDetail fileDetail = new FileDetail(fileName);
                    fileDetailMap.put(fileName, fileDetail);
                });

            }

            // 保存检测状态信息
            dailyTaskService.saveOne(dailyTask);

            //添加待导入目录信息
            dailyTaskList.add(dailyTask);
        }

        logger.info("#####扫描文件完成..");

        return dailyTaskList;
    }

}
