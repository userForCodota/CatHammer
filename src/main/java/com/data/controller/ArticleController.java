package com.data.controller;

import com.alibaba.fastjson.JSON;
import com.data.pojo.Article;
import com.data.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 分页获取articles数据
     */
    @PostMapping("/articles/{index}/{limit}")
    @ResponseBody
    public String getArticlesByPage(@PathVariable int index, @PathVariable int limit) {
        List<Article> list = articleService.getArticlesByPage(index, limit);
        list = articleService.base64ToSrc(list);
        String listJsonStr = JSON.toJSONString(list);
        return listJsonStr;
    }
}
