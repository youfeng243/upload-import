package com.haizhi.engine;

import com.haizhi.type.ImportType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Created by youfeng on 2017/7/2.
 * 导入类工程函数
 */
public class ImportFactory {
    private static Logger logger = LoggerFactory.getLogger(ImportFactory.class);

    public static ImportService select(String type) {
        for (ImportType importType : ImportType.values()) {
            if (Objects.equals(type, importType.getName())) {
                try {
                    return (ImportService) Class.forName(importType.getClazz()).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    logger.error("初始化对象失败: ", e);
                }
            }
        }

        return null;
    }
}
