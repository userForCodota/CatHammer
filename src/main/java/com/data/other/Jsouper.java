package com.data.other;


import com.data.common.Base64Utils;
import com.data.common.JsoupUtils;
import com.data.pojo.Movie;
import com.data.service.MovieService;
import com.data.service.MovieServiceImpl;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * jsoup 是一款Java 的HTML解析器，可直接解析某个URL地址、HTML文本内容。它提供了一套非常省力的API，可
 * 通过DOM，CSS以及类似于jQuery的操作方法来取出和操作数据。
 * 本类用于写jsoup的业务逻辑，但是是针对特定网页的，没有通用型，所以不写成service>>serviceImpl的流程
 */

@Service
public class Jsouper {
    @Autowired
    private MovieService movieService;

    /**
     * 从www.dytt8.net电影天堂爬取信息
     *
     * @param domain    域名
     * @param minPage   从第几页开始爬取
     * @param maxPage   爬取到第几页
     * @param prefixUrl 概览页URL的前缀
     * @param suffixUrl 概览页URL的后缀
     */
    public static void getMoviesFromInternet(String domain, int minPage, int maxPage, String prefixUrl, String suffixUrl) {
        if (domain.indexOf("https://www.dytt8.net") != -1) {
            Jsouper jsouper = new Jsouper();
            jsouper.getMoviesFromWWWDYTT8N(domain, minPage, maxPage, prefixUrl, suffixUrl);
        }
    }

    private void getMoviesFromWWWDYTT8N(String domain, int minPage, int maxPage, String prefixUrl, String suffixUrl) {
        Map<String, String> msgMap = new HashMap<>();
        minPage = minPage <= 0 ? 1 : minPage;
        //第一层：概览页循环
        for (int i = minPage; i <= maxPage; i++) {
            String overviewPageUrl = prefixUrl + i + suffixUrl;
            Connection conn = null;
            conn = Jsoup.connect(overviewPageUrl).timeout(10000);
            conn = JsoupUtils.setRequestHeader(conn);
            try {
                Document document = conn.get();
                Elements tables = document.select("table[class='tbspan']");
                //第二层，细览页循环
                Movie tempMovie;//movie容器
                String leftMark;
                String rightMark;
                for (Element table : tables) {
                    tempMovie = new Movie();
                    Elements a = table.select("a[class='ulink']");
                    String detailViewPageUrl = domain + a.attr("href");//细览页地址
                    tempMovie.setUrl(detailViewPageUrl);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存细览页地址
                    tempMovie.setTitle(a.html());//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存标题
                    Connection connSecond = Jsoup.connect(detailViewPageUrl).timeout(10000);
                    Document docDetailPage = connSecond.get();
                    Elements span = docDetailPage.select("span[style='FONT-SIZE: 12px']");
                    String text = span.text();//用.text()而不是.html()可以去掉html标签
                    leftMark = "◎片　　名";
                    rightMark = "◎年　　代";
                    String title_source = text.substring(text.indexOf(leftMark) + leftMark.length(), text.indexOf(rightMark));
                    tempMovie.setTitle_source(title_source.trim());//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存片名
                    leftMark = "◎上映日期";
                    rightMark = "◎IMDb评分";
                    String time = text.substring(text.indexOf(leftMark) + leftMark.length(), text.indexOf(rightMark));
                    tempMovie.setTime(time.trim());//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存上映时间
                    leftMark = "◎标　　签";
                    rightMark = "◎简　　介";
                    String tags = text.substring(text.indexOf(leftMark) + leftMark.length(), text.indexOf(rightMark));
                    tempMovie.setTags(tags.trim());//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存标签
                    leftMark = "◎简　　介";
                    rightMark = "【下载地址】";
                    String description = text.substring(text.indexOf(leftMark) + leftMark.length(), text.indexOf(rightMark));
                    tempMovie.setDescription(description.trim());//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存描述
                    //处理图片
                    Elements img = span.select("img");
                    String picSrc = img.get(0).attr("src");
                    tempMovie.setPic(picSrc.trim());//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存图片路径
                    String picToBase64 = Base64Utils.internetPicToBase64(picSrc);
                    tempMovie.setPicBase64(picToBase64.trim());//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存图片base64转码
                    //处理迅雷下载地址
                    Elements tbodys = docDetailPage.select("tbody");
                    Elements thunders = tbodys.select("a");
                    for (Element thunder : thunders) {
                        String href = thunder.attr("href");
                        if (href.indexOf("thunder://") == 0) {
                            tempMovie.setThunder(href);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存迅雷下载地址
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
