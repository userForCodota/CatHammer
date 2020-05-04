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
        return "list";//概览页
    }
}
