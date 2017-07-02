package com.haizhi.mongodb.service;

import com.haizhi.mongodb.model.DailyTask;

/**
 * Created by youfeng on 2017/7/2.
 * 每日任务访问接口
 */
public interface DailyTaskService {
    DailyTask findOne(String date);

    void saveOne(DailyTask dailyTask);
}
