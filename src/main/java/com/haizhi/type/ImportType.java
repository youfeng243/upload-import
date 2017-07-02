package com.haizhi.type;

/**
 * Created by youfeng on 2017/6/30.
 * 导入的数据类型
 */
public enum ImportType {

    // 工商抓取反馈
    CRAWL_GONGSHANG("gongshang_crawl_report");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ImportType(String name) {
        this.name = name;
    }
}
