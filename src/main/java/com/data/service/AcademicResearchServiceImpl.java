package com.data.service;

import com.data.common.Base64Utils;
import com.data.common.JsoupUtils;
import com.data.pojo.AcademicResearch;
import com.data.pojo.Article;
import jdk.nashorn.internal.ir.IfNode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class AcademicResearchServiceImpl implements AcademicResearchService {
    @Autowired
    @SuppressWarnings("all")
    private ArticleService articleService;

    /**
     * 总控：从AcademicResearch网站爬取视频信息，存进article数据库.
     * 思路是一层层处理，除了最后一层的detail层，之前的每一层处理的目标是获取url的集合list，然后往下传递
     */
    @Override
    public void addArticleFromAcademicResearch(AcademicResearch arh) {
        //SiteAuk只是某个网站而已
        List<String> pageList = dealFloorFirstWhenSiteAuk(arh);//获取所有的概览页地址
        if (!CollectionUtils.isEmpty(pageList)) {
            for (int i = 0; i < pageList.size(); i++) {
                String tempPageUrl = pageList.get(i);
                Set<String> detailUrlSet = dealFloorSecondWhenSiteAuk(tempPageUrl, arh);//获取一个概览页包含的所有细览页地址
                if (!CollectionUtils.isEmpty(detailUrlSet)) {
                    dealFloorThirdWhenSiteAuk(detailUrlSet, arh, (i + 1));//对概览页中每一个细览页进行爬取
                }
            }
        }
        //done
    }


    /**
     * 第一层：获取需要处理的所有页面url
     *
     * @param arh
     * @return
     */
    private static List<String> dealFloorFirstWhenSiteAuk(AcademicResearch arh) {
        List<String> list = new ArrayList<>();
        String currentListPageUrl;
        for (int i = arh.getMinPageNumber(); i <= arh.getMaxPageNumber(); i++) {
            currentListPageUrl = arh.getNumLeft() + i + arh.getNumRight();//当前列表页的url
            list.add(currentListPageUrl);
        }
        return list;
    }

    /**
     * 对某一个列表页（概览页）进行细览页地址获取
     *
     * @param currentListPageUrl 当前概览页地址
     * @return 细览页地址集合list
     */
    private static Set<String> dealFloorSecondWhenSiteAuk(String currentListPageUrl, AcademicResearch arh) {
        Set<String> detailUrlSet = new HashSet<>();
        Connection jsoupConn = Jsoup.connect(currentListPageUrl).timeout(10000);//设置10秒超时的缓冲时间，防止目标网站太卡
        jsoupConn = JsoupUtils.setRequestHeader(jsoupConn);//403/405问题的话，可以模拟浏览器header来解决
        //最后在用get方法获取
        try {
            //尝试获取
            Document document = jsoupConn.get();
            //尝试解析
            if (document != null) {
                Element targetDIV = document.getElementById("tpl-img-content");//目标a标签在这个div下面
                Elements allALabels = targetDIV.select("a[href]");//目标div下面所有的a标签，还要进一步筛选
                for (Element a : allALabels) {
                    if (a.attr("href") != null && !a.attr("href").trim().equals("")) {
                        if (a.attr("href").indexOf("/shipin/") == 0) {
                            //目标找到
                            String detailPageUrl = arh.getDomain() + "/" + (a.attr("href"));//拼接路径
                            detailUrlSet.add(detailPageUrl);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("【error】网址" + currentListPageUrl + "访问超时或读取失败");
        }
        return detailUrlSet;
    }

    /**
     * //对概览页中每一个细览页进行爬取
     *
     * @param detailUrlSet 概览页中所有细览页的set集合
     * @param arh          网站信息
     */
    private void dealFloorThirdWhenSiteAuk(Set<String> detailUrlSet, AcademicResearch arh, int currentPageNum) {
        Article article;
        Iterator<String> it = detailUrlSet.iterator();
        int count = 0;
        while (it.hasNext()) {
            count++;
            String detailPageUrl = it.next();
            Connection JsoupConn = Jsoup.connect(detailPageUrl).timeout(10000);
            JsoupConn = JsoupUtils.setRequestHeader(JsoupConn);
            try {
                Document document = JsoupConn.get();
                article = new Article();
                //在详情页开始解析：获取原始网址
                article.setUrl(detailPageUrl);
                article.setType(2);
                //在详情页开始解析：获取标题
                Elements h2s = document.select("h2[class]");
                for (Element h2 : h2s) {
                    if (h2.attr("class") != null && !h2.attr("class").trim().equals("")) {
                        if (h2.attr("class").indexOf("c_pink") != -1 && h2.attr("class").indexOf("text-ellipsis") != -1) {
                            article.setTitle(h2.text());
                            break;
                        }
                    }
                }
                //在详情页开始解析：获取迅雷下载地址
                Elements allALabels = document.select("a[href]");
                for (Element a : allALabels) {
                    if (a.attr("href") != null && a.attr("href").trim() != "") {
                        if (a.attr("href").indexOf("thunder://") == 0) {
                            article.setThunder(a.attr("href"));
                            break;
                        }
                    }
                }
                //在详情页开始解析：获取图片(这里只能获取路径，所以需要转)
                Elements imgs = document.select(".lazy");//class=lazy的img标签
                if (imgs != null && imgs.size() > 0) {
                    for (Element img : imgs) {
                        String src = img.attr("data-original");
                        article.setPic(src);
                        //获取pic之后尝试将pic转成pic_base64
                        String picBase64 = Base64Utils.internetPicToBase64(src);
                        if (picBase64 != null && picBase64.length() > 0) {
                            article.setPicBase64(picBase64);
                        }
                        break;
                    }
                }
                //解析完的话，将article对象进行持久化前处理和持久化处理
                article.setUUID(UUID.randomUUID().toString());
                List<String> allThunder = articleService.getAllThunder();
                if (!allThunder.contains(article.getThunder())) {
                    int result = articleService.addArticle(article);//插入数据库
                    if (result > 0) {
                        System.out.println("[" + currentPageNum + "," + count + "]持久化成功！");
                    } else {
                        System.out.println("[" + currentPageNum + "," + count + "]持久化失败！");
                    }
                } else {
                    System.out.println("[" + currentPageNum + "," + count + "]持久化失败！已存在相同thunder地址");
                }
            } catch (IOException e) {
                System.out.println("【error】网址" + detailPageUrl + "访问超时或读取失败");
            }
        }
    }

    //####################################################################################################################
    @Override
    public void addArticleFromAcademicResearchCategoryShort(AcademicResearch arh) {

    }
}
