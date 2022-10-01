package com.cn.tz13.bigdata.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :  by zhijun zhang
 * @date :  Created 2022/9/23 14:42
 * @description :  test
 */
public class Test {

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();

        map.put("a", "12345");
        map.put("b", "123456");
        map.put("c", "1234567");

        System.out.println(map);
    }
}
