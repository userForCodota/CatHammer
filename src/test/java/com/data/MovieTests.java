package com.data;

import com.data.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovieTests {
    @Autowired
    private MovieService movieService;

    /**
     * 从www.dytt8.net电影天堂爬取信息
     */
    @Test
    public void getMoviesFromInternet() {
        String domain = "https://www.dytt8.net/";
        int minPage = 1;
        int maxPage = 10;
        String prefixUrl = "https://www.dytt8.net/html/gndy/dyzz/list_23_";
        String suffixUrl = ".html";
        movieService.getMoviesFromInternet(domain, minPage, maxPage, prefixUrl, suffixUrl);
    }


}
