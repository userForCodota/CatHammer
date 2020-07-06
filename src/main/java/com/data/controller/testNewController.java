package com.data.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
//@RequestMapping("/api")
@CrossOrigin
public class testNewController {

    @RequestMapping("/data")
    @ResponseBody
    public String test02() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("name", "chengfeng");
        map.put("age", "29");
        map.put("love", "girl");
        return JSON.toJSONString(map);
    }
}
