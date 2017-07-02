package com.haizhi.mongodb.service;

import com.haizhi.mongodb.model.DailyTask;
import com.haizhi.mongodb.repository.DailyTaskRepository;
import com.haizhi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by youfeng on 2017/7/2.
 * 数据查找接口
 */
@Service
public class DailyTaskServiceImpl implements DailyTaskService {

    private final DailyTaskRepository dailyTaskRepository;

    @Autowired
    public DailyTaskServiceImpl(DailyTaskRepository dailyTaskRepository) {
        this.dailyTaskRepository = dailyTaskRepository;
    }

    @Override
    public DailyTask findOne(String date) {
        return dailyTaskRepository.findOne(date);
    }

    @Override
    public void saveOne(DailyTask dailyTask) {
        dailyTask.setUpdateTime(TimeUtil.getCurrentTime());
        dailyTaskRepository.save(dailyTask);
    }
}
