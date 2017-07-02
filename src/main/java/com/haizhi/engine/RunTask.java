package com.haizhi.engine;

import com.haizhi.mongodb.model.FileDetail;
import com.haizhi.mongodb.model.FolderDetail;
import com.haizhi.type.StatusType;
import com.haizhi.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by youfeng on 2017/7/2.
 * 运行容器
 */
public class RunTask implements Callable<Integer> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    //数据文件夹路径
    private String folderPath;

    // 文件下相关信息
    private FolderDetail folderDetail;

    // 引擎接口
    private ImportService importService;

    public RunTask(String folderPath, FolderDetail folderDetail) {
        this.folderPath = folderPath;
        this.folderDetail = folderDetail;

        importService = ImportFactory.select(folderDetail.getFolderName());
    }

    @Override
    public Integer call() throws Exception {

        if (importService == null) {
            return StatusType.ERROR_INIT_NULL;
        }

        for (Map.Entry<String, FileDetail> entry : folderDetail.getFileList().entrySet()) {
            FileDetail fileDetail = entry.getValue();
            if (fileDetail.getFinish()) {
                continue;
            }

            String dataPath = folderPath + fileDetail.getFileName();
            logger.info("当前需要导入的数据路径: {}", dataPath);

            //具体导入数据
            fileDetail.setFinish(importService.importData(dataPath));
            fileDetail.setUpdateTime(TimeUtil.getCurrentTime());
        }

        return StatusType.SUCCESS;
    }
}
