package com.cn.tz13.bigdata.common.config;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author :  by zhijun zhang
 * @date :  Created 2022/9/23 17:38
 * @description :  读取配置文件工具类
 */
public class ConfigUtils {

    private static final Logger LOG = Logger.getLogger(ConfigUtils.class);

    //单例模式-懒汉模式
    private static volatile ConfigUtils configUtils;
    //私有构造方法
    private ConfigUtils(){}

    //公共获取实例的方法
    public static ConfigUtils getInstance(){
        if(configUtils == null){
            synchronized (ConfigUtils.class){
                if(configUtils == null){
                    configUtils = new ConfigUtils();
                }
            }
        }
        return configUtils;
    }

    //定义一个读取配置文件的方法
    public Properties getProperties(String path){
        Properties properties= new Properties();

        try {
            LOG.info("开始读取配置文件" + path);
            InputStream insss = this.getClass().getClassLoader().getResourceAsStream(path);
            properties.load(insss);
            LOG.info("开始读取配置文件：" + path + "完成。");
        } catch (Exception e) {
            LOG.error("加载配置文件" + path +"失败");
            LOG.error(null,e);
        }
        return properties;
    }

    public static void main(String[] args) {

        String path = "kafka/kafka-server-config.properties";

        Properties properties = ConfigUtils.getInstance().getProperties(path);
        properties.keySet().forEach(key->{
            System.out.println(key);
        });
    }

}
