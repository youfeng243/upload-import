package com.haizhi.engine;

import com.haizhi.util.JsonUtils;
import com.haizhi.util.ToolUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by youfeng on 2017/7/2.
 * 工商导入类
 */
public class GongShangServiceImpl implements ImportService {

    private static Logger logger = LoggerFactory.getLogger(GongShangServiceImpl.class);

    //默认抓取优先级
    private static final int DEFAULT_LEVEL = 3;

    //默认数据key
    private static final String DEFAULT_KEY = "data";

    //http 客户端
    private OkHttpClient client = new OkHttpClient();

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    //从文件中获取数据
    private List<String> getFileList(String path) {
        List<String> lineList;

        try {
            lineList = ToolUtil.getTxtLines(path);
        } catch (IOException e) {
            logger.error("读取文件数据失败:", e);
            lineList = null;
        }

        return lineList;
    }

    //处理数据
    private Boolean processData(List<String> lineList) {

        //{'data': [{"level": 3, "company": "惠州侨兴集团有限公司有限公司"}]}

        lineList.forEach(company -> {
            Map<String, List<Item>> postData = new HashMap<>();
            Item item = new Item();
            item.setCompany(company);
            item.setLevel(DEFAULT_LEVEL);
            List<Item> items = new ArrayList<>();
            items.add(item);
            postData.put(DEFAULT_KEY, items);

            String jsonData = JsonUtils.objectToJson(postData);
            if (jsonData == null) {
                logger.warn("无法转换成json: {}", postData);
                return;
            }
            RequestBody body = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder()
                    .url("http://cs4:9876/api/updatecompanydata")
                    .post(body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                logger.info("{} {}", company, response.body().string());
            } catch (IOException e) {
                logger.error("请求服务器失败:", e);
            }
        });


        return Boolean.TRUE;
    }

    @Override
    public Boolean importData(String path) {

        //获得文件列表
        List<String> lineList = getFileList(path);
        if (lineList == null) {
            return Boolean.FALSE;
        }

        //导入数据
        if (!processData(lineList)) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private class Item {
        private int level;
        private String company;

        public int getLevel() {
            return level;
        }

        void setLevel(int level) {
            this.level = level;
        }

        public String getCompany() {
            return company;
        }

        void setCompany(String company) {
            this.company = company;
        }
    }
}
