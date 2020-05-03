package com.data;

import com.data.pojo.Article;
import com.data.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ArticleTests {
    @Autowired
    private ArticleService articleService;

    @Test
    public void getAllArticlesTest() {
        List<Article> allArticle = articleService.getAllArticle();
        for (Article article : allArticle) {
            System.out.println(article);
        }
    }




}
