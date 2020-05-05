package com.data.service;

import com.data.common.Base64Utils;
import com.data.common.JsoupUtils;
import com.data.dao.MovieMapper;
import com.data.pojo.Movie;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("all")
public class MovieServiceImpl implements MovieService {

    @Autowired
    @SuppressWarnings("all")
    private MovieMapper movieMapper;


    @Override
    public int addMovie(Movie movie) {
        return movieMapper.addMovie(movie);
    }

    @Override
    public List<Movie> allMovies() {
        return movieMapper.allMovies();
    }

    @Override
    public List<String> allThunder() {
        return movieMapper.allThunder();
    }

    @Override
    public void getMoviesFromInternet(String domain, int minPage, int maxPage, String prefixUrl, String suffixUrl) {
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
                int count = 0;
                for (Element table : tables) {
                    count++;
                    tempMovie = new Movie();
                    Elements a = table.select("a[class='ulink']");
                    String detailViewPageUrl = domain + a.attr("href");//细览页地址
                    tempMovie.setUrl(detailViewPageUrl);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存细览页地址
                    tempMovie.setTitle(a.html().substring(a.html().indexOf("《"), a.html().indexOf("》") + 1));//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存标题
                    Connection connSecond = Jsoup.connect(detailViewPageUrl).timeout(10000);
                    Document docDetailPage = connSecond.get();
                    Elements span = docDetailPage.select("span[style='FONT-SIZE: 12px']");
                    String text = span.text();//用.text()而不是.html()可以去掉html标签
                    leftMark = "◎片　　名";
                    rightMark = "◎年　　代";
                    if (text.indexOf(leftMark) != -1 && text.indexOf(rightMark) != -1) {
                        String title_source = text.substring(text.indexOf(leftMark) + leftMark.length(), text.indexOf(rightMark));
                        tempMovie.setTitle_source(title_source.trim());//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存片名
                    }
                    leftMark = "◎上映日期";
                    rightMark = "◎IMDb评分";
                    if (text.indexOf(leftMark) != -1 && text.indexOf(rightMark) != -1) {
                        String time = text.substring(text.indexOf(leftMark) + leftMark.length(), text.indexOf(rightMark));
                        tempMovie.setTime(time.trim());//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存上映时间
                    }
                    leftMark = "◎标　　签";
                    rightMark = "◎简　　介";
                    if (text.indexOf(leftMark) != -1 && text.indexOf(rightMark) != -1) {
                        String tags = text.substring(text.indexOf(leftMark) + leftMark.length(), text.indexOf(rightMark));
                        tempMovie.setTags(tags.trim());//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存标签
                    }
                    leftMark = "◎简　　介";
                    rightMark = "【下载地址】";
                    if (text.indexOf(leftMark) != -1 && text.indexOf(rightMark) != -1) {
                        String description = text.substring(text.indexOf(leftMark) + leftMark.length(), text.indexOf(rightMark));
                        tempMovie.setDescription(description.trim());//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存描述
                    }
                    //处理图片
                    Elements img = span.select("img");
                    if (img.size() > 0) {
                        String picSrc = img.get(0).attr("src");
                        tempMovie.setPic(picSrc.trim());//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存图片路径
                        String picToBase64 = Base64Utils.internetPicToBase64(picSrc);
                        tempMovie.setPicBase64(picToBase64.trim());//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>存图片base64转码
                    }
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
                    int result = movieMapper.addMovie(tempMovie);
                    if (result > 0) {
                        System.out.println("[" + i + "," + count + "]插入成功！" + tempMovie.getTitle());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
