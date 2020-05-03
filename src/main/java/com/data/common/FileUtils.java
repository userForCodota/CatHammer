package com.data.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 关于文件的工具类
 */
public class FileUtils {

    /**
     * 加载外部的properties文件，并转换成map
     *
     * @return
     */
    public static Map<String, String> loadExternalPropertoesFile(String filePath) {
        Map<String, String> resultMap = new HashMap<>();
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (bfr == null) {
            return null;
        }
        Properties properties = new Properties();
        try {
            properties.load(bfr);
            Iterator<Map.Entry<Object, Object>> it = properties.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Object, Object> entry = it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (key != null) {
                    resultMap.put(key.toString(), value == null ? "" : value.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return resultMap;
        }

    }
}
