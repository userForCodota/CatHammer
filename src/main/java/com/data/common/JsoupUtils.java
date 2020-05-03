package com.data.common;


import org.jsoup.Connection;

/**
 * 工具类，封装关于Jsoup的工具
 */
public class JsoupUtils {


    /**
     * 403/405问题的话，可以模拟浏览器header来解决，有些情况下Java代码连http会被拦截，这里为jsoup添加各种浏览器header信息，模仿真实访问
     */
    public static Connection setRequestHeader(Connection jsoupConnection) {
        jsoupConnection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        jsoupConnection.header("Accept-Encoding", "gzip, deflate, sdch");
        jsoupConnection.header("Accept-Language", "zh-CN,zh;q=0.8");
        jsoupConnection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        return jsoupConnection;
    }
}
