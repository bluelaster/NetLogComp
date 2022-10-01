package com.cn.tz13.bigdata.flume.interceptor;

import com.alibaba.fastjson.JSON;
import com.cn.tz13.bigdata.flume.constant.ConstantsFields;
import com.cn.tz13.bigdata.flume.service.RecordDataCheck;

import org.apache.commons.io.Charsets;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author :  by zhijun zhang
 * @date :  Created 2022/9/27 17:13
 * @description :  Net Log Interceptor
 */
public class LogInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        Event eventNew = new SimpleEvent();

        Map<String, String> headers = event.getHeaders();
        String fileName = headers.get(ConstantsFields.FILE_NAME);
        String absolute_fileName = headers.get(ConstantsFields.ABSOLUTE_FILENAME);

        String line = new String(event.getBody(), Charsets.UTF_8);

        //TODO 对数据进行处理
        //清洗，转换，加工

        Map map = RecordDataCheck.parseAndValidate(fileName,absolute_fileName,line);

        if(map == null){
           return null;
        }else{
            String json = JSON.toJSONString(map);
            eventNew.setBody(json.getBytes());
            return eventNew;
        }
    }

    @Override
    public List<Event> intercept(List<Event> events) {

        List<Event> eventsNew = new ArrayList<>();

        events.forEach(event -> {
            Event eventNew = intercept(event);
            if(eventNew != null){
                eventsNew.add(eventNew);
            }
        });

        return eventsNew;
    }

    //内部类构造拦截器
    public static class Builder implements Interceptor.Builder{

        //build
        @Override
        public Interceptor build() {
            return new LogInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }

    @Override
    public void close() {

    }
}
