package com.cn.tz13.bigdata.kafka.producer;


import com.cn.tz13.bigdata.kafka.config.KafkaConfig;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.log4j.Logger;

/**
 * @author :  by zhijun zhang
 * @date :  Created 2022/9/24 20:32
 * @description :  kafka字符串生产者
 */
public class StringProducer {
    private static final Logger LOG = Logger.getLogger(StringProducer.class);

    //定义一个发送数据方法
    public static void proceducer(String topic, String line) {
        try {
            //构造一个消息
            KeyedMessage<String, String> keyedMessage = new KeyedMessage<>(topic, line);
            //构造一个生产者
            //需要一个ProducerConfig
            ProducerConfig producerConfig = KafkaConfig.getInstance().getProducerConfig();
            Producer<String, String> producer = new Producer<>(producerConfig);
            producer.send(keyedMessage);
            LOG.info("向kafka" + topic + "发送数据成功");

        } catch (Exception e) {
            LOG.info("向kafka" + topic + "发送数据失败");
            LOG.error(null, e);
        }
    }

    public static void main(String[] args) {
        StringProducer.proceducer("test1", "611111111111");
    }
}
