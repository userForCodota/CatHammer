package com.data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CommonController {

    @RequestMapping("/index")
    public String goIndex() {
        return "index";
    }

    @RequestMapping("/list")
    public String goList() {
        return "List_AcademicResearch";//概览页
    }

    @RequestMapping("/movie")
    public String goMovieList() {
        return "List_Movie";//概览页
    }
}
