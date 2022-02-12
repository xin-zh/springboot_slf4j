package com.example.demo.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * Created by 25790 on 2021/12/26.
 */
public class Producer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //1.创建消息生产者，指定生成组名
        DefaultMQProducer producer = new DefaultMQProducer("rocketmq-group");
        //2.指定NameServer的地址
        producer.setNamesrvAddr("192.168.232.129:9876");
        //3.启动生产者
        producer.start();
        Message message = new Message("roketmq-topic", "test-tag", "test rocketmq".getBytes());
        SendResult result = producer.send(message);
        System.out.println("SendResult——>"+result);
        producer.shutdown();
    }
}
