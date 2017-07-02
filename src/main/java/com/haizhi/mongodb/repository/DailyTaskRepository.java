package com.haizhi.mongodb.repository;

import com.haizhi.mongodb.model.DailyTask;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by youfeng on 2017/7/2.
 */
public interface DailyTaskRepository extends MongoRepository<DailyTask, String> {
    DailyTask findByDate(String date);
}
