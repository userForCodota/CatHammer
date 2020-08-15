package com.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.data.common.OutsideFileUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OutsideFileUtils使用的tests
 */
public class OutsideFileUtilsTests {

    private OutsideFileUtils outsideFileUtils = new OutsideFileUtils();

    @Test
    public void test1() throws Exception {
        String path = OutsideFileUtilsTests.class.getClassLoader().getResource("json/text-align-advise.json").getPath();
        String jsonStr = this.outsideFileUtils.loadJson(path);
        System.out.println(jsonStr);
        JSONObject object = JSON.parseObject(jsonStr);
        System.out.println(object.get("全局说明1").toString());
    }

    @Test
    public void test2() {
        List<Map<String, Object>> tempResult = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        map1.put("tagname", "tagname1");
        tempResult.add(map1);
        map2.put("tagname", "tagname2");
        tempResult.add(map2);
        System.out.println(JSON.toJSONString(tempResult));
    }

}
