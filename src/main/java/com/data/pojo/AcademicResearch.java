package com.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 学术研究网址，用于封装对象信息方便传递，不作持久化操作
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AcademicResearch {
    private String domain;//网站首页
    private int minPageNumber;//期望爬取的最小页码
    private int maxPageNumber;//期望爬取的最大页码
    private String numLeft; //url中页码左边的统一字符串，用于拼接url
    private String numRight;//url中页码右边的统一字符串，用于拼接url
}
