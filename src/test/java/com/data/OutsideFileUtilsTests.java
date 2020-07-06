package com.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.data.common.OutsideFileUtils;
import org.junit.jupiter.api.Test;

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
}
