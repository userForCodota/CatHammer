package com.data.dao;

import com.data.pojo.Movie;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MovieMapper {

    @Insert("INSERT INTO academic_research.movies(title, title_source, time, type, tags, description, thunder, url, pic, picBase64) VALUES " +
            "(#{title}, #{title_source}, #{time}, #{type}, #{tags}, #{description}, #{thunder}, #{url}, #{pic}, #{picBase64})")
    int addMovie(Movie movie);

    @Select("SELECT * FROM movies")
    List<Movie> allMovies();

    @Select("SELECT thunder FROM movies")
    List<String> allThunder();

}
