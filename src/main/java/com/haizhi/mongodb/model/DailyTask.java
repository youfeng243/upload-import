package com.haizhi.mongodb.model;

import com.haizhi.util.TimeUtil;
import org.springframework.data.annotation.Id;

import java.util.Map;

/**
 * Created by youfeng on 2017/7/2.
 * 每日导入任务模型
 */
public class DailyTask {

    // 当前数据日期 2017-07-01
    @Id
    private String date;

    // 不同类型数据导入
    private Map<String, FolderDetail> folderDetailMap;

    // 任务创建时间
    private String createTime;

    // 任务更新时间
    private String updateTime;

    public DailyTask(String date) {
        this.date = date;
        this.createTime = this.updateTime = TimeUtil.getCurrentTime();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, FolderDetail> getFolderDetailMap() {
        return folderDetailMap;
    }

    public void setFolderDetailMap(Map<String, FolderDetail> folderDetailMap) {
        this.folderDetailMap = folderDetailMap;
    }
}
