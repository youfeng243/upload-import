package com.haizhi.mongodb.model;

import java.util.Map;

/**
 * Created by youfeng on 2017/6/30.
 * 不同文件解析详情
 */
public class FolderDetail {

    // 导入的数据类型，不同的数据类型由不同的导入引擎解析
    private String importType;

    // 文件列表
    private Map<String, FileDetail> fileDetailMap;

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    public Map<String, FileDetail> getFileDetailMap() {
        return fileDetailMap;
    }

    public void setFileDetailMap(Map<String, FileDetail> fileDetailMap) {
        this.fileDetailMap = fileDetailMap;
    }
}
