package com.data.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Movie {
    private int id;
    private String title;
    private String title_source;
    private String time;
    private String type;
    private String tags;
    private String description;
    private String thunder;
    private String url;
    private String pic;
    private String picBase64;
}
