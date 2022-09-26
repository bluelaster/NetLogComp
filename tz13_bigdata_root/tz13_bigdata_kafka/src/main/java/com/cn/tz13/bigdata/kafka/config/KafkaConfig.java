package com.cn.tz13.bigdata.kafka.config;

import com.cn.tz13.bigdata.common.config.ConfigUtils;
import kafka.producer.ProducerConfig;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * @author :  by zhijun zhang
 * @date :  Created 2022/9/24 19:51
 * @description :  读取kafka配置信息
 */
public class KafkaConfig {

    private static final Logger LOG = Logger.getLogger(KafkaConfig.class);

    //定义kafka配置路径
    private static final String DEFAULT_KAFKA_CONFIG_PATH = "kafka/kafka-server-config.properties";

    //kafka的配置文件
    private Properties properties;
    private ProducerConfig producerConfig;

    //定义一个私有静态变量
    private static volatile KafkaConfig kafkaConfig = null;

    //私有构造方法
    private KafkaConfig(){
        //读取配置文件
        LOG.info("开始实例化producerConfig");
        properties = ConfigUtils.getInstance().getProperties(DEFAULT_KAFKA_CONFIG_PATH);
        producerConfig = new ProducerConfig(properties);
        LOG.info("实例化producerConfig成功");
    }

    //获取实例的公共静态方法，单例
    public static KafkaConfig getInstance(){
        if(kafkaConfig == null){
            synchronized (KafkaConfig.class){
                if(kafkaConfig == null){
                    kafkaConfig = new KafkaConfig();
                }
            }
        }
        return kafkaConfig;
    }

    public ProducerConfig getProducerConfig(){
        return producerConfig;
    }
}
