package com.haizhi.mongodb.model;

import com.haizhi.util.TimeUtil;

/**
 * Created by youfeng on 2017/6/30.
 * 基本数据块文件结构信息
 */
public class FileDetail {

    // 文件名
    private String fileName;

    // 是否已经完成数据导入
    private Boolean finish;

    //文件最开始存在的时间
    private String createTime;

    //文件数据导入时间
    private String updateTime;

    public FileDetail(String fileName) {
        this.fileName = fileName;
        this.finish =Boolean.FALSE;
        this.createTime = this.updateTime = TimeUtil.getCurrentTime();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
