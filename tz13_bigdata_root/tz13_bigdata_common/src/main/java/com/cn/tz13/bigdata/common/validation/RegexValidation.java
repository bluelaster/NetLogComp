package com.cn.tz13.bigdata.common.validation;

import com.cn.tz13.bigdata.common.Constant.RegexConstant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author :  by zhijun zhang
 * @date :  Created 2022/9/28 11:31
 * @description :  校验
 */
public class RegexValidation {



    /**
     * 判断字段是否为Email 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isEmail(String str) {
        return Regular(str, RegexConstant.EMAIL);
    }

    /**
     * 判断是否为电话号码 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isPhone(String str) {
        return Regular(str, RegexConstant.PHONE);
    }

    /**
     * 判断是否为手机号码 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isMobile(String str) {
        return Regular(str, RegexConstant.MOBILE);
    }

    /**
     * 判断是否为手机号码 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isMac(String str) {
        return Regular(str, RegexConstant.MAC);
    }

    /**
     * 判断经度是否合规 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isLonglait(String str) {
        return Regular(str, RegexConstant.LONGITUDE);
    }

    /**
     * 判断维度是否合规 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isLatitude(String str) {
        return Regular(str, RegexConstant.LATITUDE);
    }


    /**
     * 匹配是否符合正则表达式pattern 匹配返回true
     *
     * @param str       匹配的字符串
     * @param pattern   匹配模式
     * @return boolean
     */
    private static boolean Regular(String str, String pattern) {
        if (null == str || str.trim().length() <= 0)
            return false;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

}
