package com.cn.tz13.bigdata.flume.sink;

import com.cn.tz13.bigdata.kafka.producer.StringProducer;
import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.apache.log4j.Logger;

/**
 * @author :  by zhijun zhang
 * @date :  Created 2022/9/21 21:19
 * @description :  sink DEMO class
 */
public class MySink extends AbstractSink implements Configurable {
    private String topic;

    private static final Logger LOG = Logger.getLogger(MySink.class);

    @Override
    public void configure(Context context) {
        String topic = context.getString("topic");
    }

    @Override
    public void start() {
        // Initialize the connection to the external repository (e.g. HDFS) that
        // this Sink will forward Events to ..
    }

    @Override
    public void stop () {
        // Disconnect from the external respository and do any
        // additional cleanup (e.g. releasing resources or nulling-out
        // field values) ..
    }

    @Override
    public Status process() throws EventDeliveryException {
        Status status = null;

        LOG.info("=====================开始执行sink=======================");
        // Start transaction
        Channel ch = getChannel();
        Transaction txn = ch.getTransaction();
        txn.begin();
        try {
            // This try clause includes whatever Channel operations you want to do
            Event event = ch.take();

            if(null == event){
                txn.rollback();
                return Status.BACKOFF;
            }
            String lines = new String(event.getBody());
            //TODO: 数据已经拿到，直接往kafka里面写入就可以了，缺一个写kafka的API
            StringProducer.proceducer(topic,lines);
            LOG.info(lines.toString());

            txn.commit();
            status = Status.READY;
        } catch (Throwable t) {
            txn.rollback();
            // Log exception, handle individual exceptions as needed
            status = Status.BACKOFF;
            // re-throw all Errors
            if (t instanceof Error) {
                throw (Error)t;
            }
        }finally {
            txn.close();
        }
        return status;
    }
}
