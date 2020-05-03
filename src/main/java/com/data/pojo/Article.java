package com.data.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Article {
    private int id;
    private String UUID;
    private int type;
    private String title;
    private String pic;//图片url
    private String picBase64;//图片转成base64后的编码
    private String thunder;
    private String description;//描述
    private String url;//原始网址
}
