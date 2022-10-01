package com.cn.tz13.bigdata.flume.service;

import com.cn.tz13.bigdata.common.config.ConfigUtils;
import com.cn.tz13.bigdata.flume.constant.ConstantsFields;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * @author :  by zhijun zhang
 * @date :  Created 2022/9/27 18:08
 * @description :  数据清洗
 */
public class RecordDataCheck {

    private static final Logger LOG = Logger.getLogger(RecordDataCheck.class);

    static Properties properties;

    static{
        String fieldsPath = "common/datatype.properties";
        properties = ConfigUtils.getInstance().getProperties(fieldsPath);
    }


    /**
     *
     * @param fileName           文件名
     * @param absolute_fileName   文件绝对路径名
     * @param line               记录内容
     * @return
     */
    public static Map parseAndValidate(String fileName, String absolute_fileName, String line){

        Map dataMap = new HashMap<String, String>();
        Map dirtyDataMap = new HashMap<String, String>();

        //清洗、转换、价格
        //imei imsi longitude latitude phone_mac device_mac device_number collect_time username phone object_username send_message accept_message message_time
        //324354578998989	324354578998989	24.000000	25.000000	1c-41-cd-ae-4f-4f	1c-41-cd-ae-4f-4f	32109231	1557305985	weixin2	186094322221	judy
        //渠道类型channel
        String channel = fileName.split("_")[0].toLowerCase();
        //根据渠道类型获取数据字典。
        String[] fieldNames = properties.get(channel).toString().split(",");
        String[] fieldDatas = line.split("\t");
        if(fieldNames.length == fieldDatas.length){
            for(int i=0; i<fieldNames.length; i++){
                dataMap.put(fieldNames[i], fieldDatas[i]);
            }
            //数据加工，根据业务需求进行调整。
            //1、没有唯一ID，可以解决数据重复消费的问题
            dataMap.put("id", UUID.randomUUID().toString().replace("-",""));
            //2、添加数据渠道
            dataMap.put("channel", channel);

            dataMap.put("rksj",System.currentTimeMillis()/1000+"");
            dataMap.put(ConstantsFields.FILE_NAME,fileName);
            dataMap.put(ConstantsFields.ABSOLUTE_FILENAME,absolute_fileName);
        }else{
            //错误数据
            dirtyDataMap.put("leng_error", "字段和值的个数不匹配");
            dirtyDataMap.put("leng", "字段和值的个数不匹配,字段的个数为"+fieldNames.length + "\t" + "实际数据个数" + fieldDatas.length);
        }

        //清洗数据，数据校验
        if(dataMap != null && dataMap.size() > 0){
            dirtyDataMap = DataValidation.validateData(dataMap);
        }

        LOG.info("验证后dirtyDataMap：" + dirtyDataMap);

        if(dirtyDataMap.size() > 0){
            dataMap = null;
            //写入 ES 写mysql
        }
        return dataMap;
    }
}
