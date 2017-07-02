package com.haizhi.engine;

import com.haizhi.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by youfeng on 2017/7/2.
 * 工商导入类
 */
public class GongShangServiceImpl implements ImportService {

    private static Logger logger = LoggerFactory.getLogger(GongShangServiceImpl.class);


    @Override
    public Boolean importData(String path) {

        List<String> lineList;

        try {
            lineList = ToolUtil.getTxtLines(path);
        } catch (IOException e) {
            logger.error("读取文件数据失败:", e);
            lineList = null;
        }

        if (lineList == null) {
            return Boolean.FALSE;
        }


        return Boolean.TRUE;
    }
}
