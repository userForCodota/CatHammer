package com.data.dao;

import com.data.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Select("SELECT * FROM articles")
    List<Article> getAllArticle();

    @Insert("INSERT INTO academic_research.articles (UUID,type,title,pic,thunder,description,url,picBase64) VALUES (#{UUID},#{type},#{title},#{pic},#{thunder},#{description},#{url},#{picBase64})")
    int addArticle(Article article);

    @Select("SELECT thunder FROM academic_research.articles")
    List<String> getAllThunder();

    @Select("SELECT * FROM academic_research.articles LIMIT #{index},#{limit}")
    List<Article> getArticlesByPage(@Param("index") int index, @Param("limit") int limit);
}
