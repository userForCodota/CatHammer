package com.data.service;

import com.data.pojo.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    List<Article> getAllArticle();//查询所有文章

    int addArticle(Article article);//增加一篇文章

    List<String> getAllThunder();//获取所有的下载地址，目的是通过下载地址做去重判断


}
