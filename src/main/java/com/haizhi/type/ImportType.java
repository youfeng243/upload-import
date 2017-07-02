package com.haizhi.type;

/**
 * Created by youfeng on 2017/6/30.
 * 导入的数据类型
 */
public enum ImportType {

    // 工商抓取反馈
    CRAWL_GONGSHANG("gongshang_crawl_report", "com.haizhi.engine.GongShangServiceImpl");

    private String name;

    private String clazz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    ImportType(String name, String clazz) {
        this.clazz = clazz;
        this.name = name;
    }
}
