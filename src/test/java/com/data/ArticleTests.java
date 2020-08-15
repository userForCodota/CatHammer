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

    @Test
    public void getArticlesByPageTests() {
        List<Article> articlesByPage = articleService.getArticlesByPage(0, 10);
        for (int i = 0; i < articlesByPage.size(); i++) {
            System.out.println(articlesByPage.get(i).getThunder());
        }
    }

    @Test
    public void test33() {
        Integer a = 1;
        Integer b = 1;
        System.out.println(a == b);
    }

}
