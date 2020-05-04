package com.data.service;

import com.data.pojo.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    List<Article> getAllArticle();//查询所有文章

    int addArticle(Article article);//增加一篇文章

    List<String> getAllThunder();//获取所有的下载地址，目的是通过下载地址做去重判断

    List<Article> getArticlesByPage(int index, int limit);//分页获取数据


    List<Article> base64ToSrc(List<Article> list);//数据库存的是base64的图片地址，这里可以拼成前端需要的src的样式：<img src=“data:image/png;base64,************"/>，其中png和后面的******是变量

}
