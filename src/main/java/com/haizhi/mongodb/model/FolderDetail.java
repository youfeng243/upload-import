package com.haizhi.mongodb.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by youfeng on 2017/6/30.
 * 不同文件解析详情
 */
public class FolderDetail {

    // 导入的数据类型，不同的数据类型由不同的导入引擎解析
    private String folderName;

    // 文件列表
    private Map<String, FileDetail> fileList;

    public FolderDetail(String importType) {
        this.folderName = importType;
        this.fileList = new HashMap<>();
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Map<String, FileDetail> getFileList() {
        return fileList;
    }

    public void setFileList(Map<String, FileDetail> fileList) {
        this.fileList = fileList;
    }
}
