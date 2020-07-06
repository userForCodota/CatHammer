package com.data.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@RequestMapping("/test")
public class TestController {
    @RequestMapping
    public String test01() {
        System.out.println("2020.05.21");
        return "test";
    }




}
