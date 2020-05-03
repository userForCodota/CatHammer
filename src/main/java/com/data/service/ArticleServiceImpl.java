package com.data.service;

import com.data.dao.ArticleMapper;
import com.data.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
