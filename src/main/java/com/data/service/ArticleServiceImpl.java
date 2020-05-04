package com.data.service;

import com.data.dao.ArticleMapper;
import com.data.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    @SuppressWarnings("all")
    private ArticleMapper articleMapper;

    @Override
    public List<Article> getAllArticle() {
        return articleMapper.getAllArticle();
    }

    @Override
    public int addArticle(Article article) {
        return articleMapper.addArticle(article);
    }

    @Override
    public List<String> getAllThunder() {
        return articleMapper.getAllThunder();
    }

    @Override
    public List<Article> getArticlesByPage(int index, int limit) {
        return articleMapper.getArticlesByPage(index, limit);
    }

    @Override
    public List<Article> base64ToSrc(List<Article> list) {
        StringBuffer sb;
//        data:image/png;base64,***
        for (int i = 0; i < list.size(); i++) {
            sb = new StringBuffer();
            sb.append("data:image/").append(list.get(i).getPic().substring(list.get(i).getPic().lastIndexOf(".") + 1)).append(";base64,").append(list.get(i).getPicBase64());
            list.get(i).setPicBase64(sb.toString());
        }
        return list;
    }
}
