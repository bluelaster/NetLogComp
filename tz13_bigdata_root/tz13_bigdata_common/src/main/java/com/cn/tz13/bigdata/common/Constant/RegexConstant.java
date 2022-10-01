package com.cn.tz13.bigdata.common.Constant;

/**
 * @author :  by zhijun zhang
 * @date :  Created 2022/9/28 14:57
 * @description :  正则 常量
 */
public class RegexConstant {

    /**
     *  验证Email的正则表达式
     *  ^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$
     */
    public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     *  验证电话号码的正则表达式
     *  ^(\(\d{3,4}-)|\d{3.4}-)?\d{7,8}$
     */
    public static final String PHONE = "^(\\(\\d{3,4}-)|\\d{3.4}-)?\\d{7,8}$";

    /**
     *  验证手机号码的正则表达式
     *  ^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$
     */
    public static final String MOBILE = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

    /**
     *  验证mac的正则表达式
     *  ^([0-9A-Fa-f]{2})(-[0-9A-Fa-f]{2}){5}
     */
    public static final String MAC = "^([0-9A-Fa-f]{2})(-[0-9A-Fa-f]{2}){5}";

    /**
     *  验证经度的正则表达式
     * ^-?(([1-9]\d?)|(1[0-7]\d)|180)(\.\d{1,8})?$
     */
    public static final String LONGITUDE = "^-?(([1-9]\\d?)|(1[0-7]\\d)|180)(\\.\\d{1,8})?$";

    /**
     *  验证维度的正则表达式
     *  ^-?(([1-8]\d?)|([1-8]\d)|90)(\.\d{1,8})?$
     */
    public static final String LATITUDE = "^-?(([1-8]\\d?)|([1-8]\\d)|90)(\\.\\d{1,8})?$";



    /**
     *  验证正整数的正则表达式
     *  ^[1-9]\\d*|0$
     */
    public static final String INTEGER_POSITIVE = "^[1-9]\\d*|0$";

    /**
     *  验证负整数的正则表达式
     *  ^-[1-9]\d*|0$
     */
    public static final String INTEGER_NEGATIVE = "^-[1-9]\\d*|0$";



}
