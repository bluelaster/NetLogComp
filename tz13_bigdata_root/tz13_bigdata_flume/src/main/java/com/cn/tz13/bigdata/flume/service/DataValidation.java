package com.cn.tz13.bigdata.flume.service;

import com.cn.tz13.bigdata.common.validation.RegexValidation;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :  by zhijun zhang
 * @date :  Created 2022/9/28 10:32
 * @description :  清洗、校验
 */
public class DataValidation {

    private static final Logger LOG = Logger.getLogger(DataValidation.class);

    public static Map<String, Object> validateData(Map<String, String> dataMap){

        if(dataMap == null){
            return null;
        }

        //LOG.info("校验记录数据："+ dataMap);

        //存放错误信息
        Map<String, Object> errorMap = new HashMap<>();
        //一个字段一个字段的校验
        //校验手机号码
        mobileValidate(dataMap,errorMap);
        //校验mac
        macValidation(dataMap,errorMap);
        //校验经度
        longlaitValidation(dataMap,errorMap);
        //校验纬度
        latitudeValidation(dataMap,errorMap);
        //定义自己的清洗规则
        //TODO 大小写统一
        //TODO 时间类型统一
        //TODO 数据字段统一
        //TODO 业务字段转换
        //TODO 数据矫正
        //TODO 验证MAC不能为空
        //TODO 验证IMSI不能为空
        //TODO 验证 QQ IMSI IMEI
        //TODO 验证DEVICENUM是否为空 为空返回错误
        //devicenumValidation(map,errorMap);
        //TODO 验证CAPTURETIME是否为空 为空过滤   不为10，14位数字过滤
        //capturetimeValidation(map,errorMap);
        //TODO 验证EMAIL
        //emailValidation(map,errorMap);
        //TODO 验证STARTTIME ENDTIME LOGINTIME LOGOUTTIME
        //timeValidation(map,errorMap);

        //LOG.info("校验发生错误信息："+ errorMap);

        return errorMap;
    }

    /**
     *  手机号码验证
     * @param dataMap
     * @param errorMap
     */
    public static void mobileValidate(Map<String, String> dataMap,Map<String, Object> errorMap){
        if(dataMap.containsKey("phone")){
            String mobile = dataMap.get("phone");

            boolean isMobile = RegexValidation.isMobile(mobile);
            if(!isMobile){
                LOG.error("手机号码格式不对， 号码为：" + mobile);

                errorMap.put("MOBILE",mobile);
                errorMap.put("MOBILE_ERROR","手机号码格式不对");
            }
        }
    }

    public static void macValidation(Map<String, String> dataMap,Map<String, Object> errorMap){
        if(dataMap.containsKey("mac")){
            String mac = dataMap.get("phone_mac");

            boolean isMac = RegexValidation.isMac(mac);
            if(!isMac){
                LOG.error("MAC地址格式不对：" + mac);

                errorMap.put("MAC",mac);
                errorMap.put("MAC_ERROR","MAC_ERRORCODE");
            }
        }
    }

    public static void longlaitValidation(Map<String, String> dataMap,Map<String, Object> errorMap){
        if(dataMap.containsKey("longlait")){
            String longlait = dataMap.get("longlait");

            boolean isLonglait = RegexValidation.isLonglait(longlait);
            if(!isLonglait){
                LOG.error("经度格式不对：" + longlait);

                errorMap.put("LONGLAIT",longlait);
                errorMap.put("LONGLAIT_ERROR","经度格式不对");
            }
        }
    }

    public static void latitudeValidation(Map<String, String> dataMap,Map<String, Object> errorMap){
        if(dataMap.containsKey("latitude")){
            String latitude = dataMap.get("latitude");

            boolean isLatitude = RegexValidation.isLatitude(latitude);
            if(!isLatitude){
                LOG.error("维度格式不对：" + latitude);

                errorMap.put("LATITUDE",latitude);
                errorMap.put("LATITUDE_ERROR","维度格式不对");
            }
        }
    }
}
