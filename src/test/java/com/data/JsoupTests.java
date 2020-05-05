package com.data;

import com.data.common.JsoupUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * 关于Jsoup的测试
 */
@SpringBootTest
public class JsoupTests {
    /**
     * 测试能否获取一个网页
     */
    @Test
    public void getPage() throws IOException {
        String url = "https://dytt8.net/html/gndy/dyzz/list_23_3.html";
        Connection conn = Jsoup.connect(url).timeout(100000);
        conn = JsoupUtils.setRequestHeader(conn);
        Document document = conn.get();
        System.out.println(document);
    }


}
