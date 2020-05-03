package com.data.service;

import com.data.pojo.AcademicResearch;
import org.springframework.stereotype.Service;

@Service
public interface AcademicResearchService {
    void addArticleFromAcademicResearch(AcademicResearch arh);
}
