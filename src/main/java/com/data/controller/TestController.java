package com.data.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/test")
public class TestController {
    @RequestMapping
    public String test01() {
        System.out.println("2020.05.21");
        return "test";
    }
}
