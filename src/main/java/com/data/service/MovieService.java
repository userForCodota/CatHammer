package com.data.service;

import com.data.pojo.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {
    int addMovie(Movie movie);

    List<Movie> allMovies();

    List<String> allThunder();

    void getMoviesFromInternet(String domain, int minPage, int maxPage, String prefixUrl, String suffixUrl);

}
