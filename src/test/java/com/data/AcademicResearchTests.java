package com.data;

import com.data.common.FileUtils;
import com.data.pojo.AcademicResearch;
import com.data.service.AcademicResearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;


@SpringBootTest
public class AcademicResearchTests {
    @Autowired
    private AcademicResearchService academicResearchService;

    /**
     * 从AcademicResearch网站爬取视频信息，存进article数据库
     */
    @Test
    public void addArticleFromAcademicResearch() {
        Map<String, String> props = FileUtils.loadExternalPropertoesFile("E:\\桌面\\外部加载属性AcademicResearch.properties");
        AcademicResearch arh = new AcademicResearch();
        arh.setDomain(props.get("domain"));//因为爬虫期间下级访问请求可能需要拼接域名(其a标签是相对路径)
        arh.setNumLeft(props.get("numLeft"));
        arh.setNumRight(props.get("numRight"));
        arh.setMinPageNumber(Integer.parseInt(props.get("minPageNumber")));
        arh.setMaxPageNumber(Integer.parseInt(props.get("maxPageNumber")));
        academicResearchService.addArticleFromAcademicResearch(arh);
    }


}
