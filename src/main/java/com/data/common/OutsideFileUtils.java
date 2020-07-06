package com.data.common;

import java.io.*;

/**
 * 专门用于加载外部文件，比如json文件和properties文件。
 */
public class OutsideFileUtils {


    /**
     * 加载外部properties文件
     * 拓展：Java读取Properties文件的六种方法https://blog.csdn.net/qq_37939251/article/details/82926984
     */
    public static void loadProperties() {

    }

    /**
     * 加载外部json文件
     * <p>
     * 拓展：Java读取本地json文件  https://www.cnblogs.com/wkfvawl/p/11876107.html
     * <p>
     * 重要理解：InputStreamReader流 https://blog.csdn.net/ai_bao_zi/article/details/81133476
     *
     * @param jsonFilePath 文件地址
     */
    public static String loadJson(String jsonFilePath) throws Exception {
        StringBuffer sb = new StringBuffer();
        Reader reader = new InputStreamReader(new FileInputStream(jsonFilePath), "UTF-8");//通过桥梁将字节转字符
        BufferedReader bufferedReader = new BufferedReader(reader);//字符流增强一下
        int temp = 0;//篮子
        while ((temp = bufferedReader.read()) != -1) {
            sb.append((char) temp);
        }
        reader.close();
        bufferedReader.close();
        return sb.toString();
    }
}
