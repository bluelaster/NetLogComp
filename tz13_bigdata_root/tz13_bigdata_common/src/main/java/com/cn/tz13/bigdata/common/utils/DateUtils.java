package com.cn.tz13.bigdata.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author :  by zhijun zhang
 * @date :  Created 2022/9/22 20:44
 * @description :  日期工具类
 */
public class DateUtils {

    public static void main(String[] args) {
        System.out.println(DateUtils.getDate("yyyy-MM-dd"));
    }

    public static String getDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date dt = new Date();
        return sdf.format(dt);
    }
}
