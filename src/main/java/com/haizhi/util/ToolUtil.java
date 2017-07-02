package com.haizhi.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by youfeng on 2017/7/2.
 * 工具类
 */
public class ToolUtil {

    public static List<String> getTxtLines(String filePath) throws IOException {

        List<String> lineList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath)));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            lineList.add(line.trim());
        }
        br.close();

        return lineList;
    }

    public static void main(String... args) throws IOException {
        List<String> lineList = getTxtLines("/tmp/server-upload-dir/upload-import/2017-07-01/gongshang_crawl_report/company_data");

        lineList.forEach(System.out::println);
    }
}
