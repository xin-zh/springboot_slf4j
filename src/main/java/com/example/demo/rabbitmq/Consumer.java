package com.example.demo.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.*;
import com.sun.org.apache.bcel.internal.generic.FADD;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by 25790 on 2021/12/25.
 */
public class Consumer {
    private final static String QUEUE_NAME="rabbitMQ.test";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false,false,false,null);
        System.out.println("Consumer Waiting Received Message");
        //告诉服务器我们需要消费哪个频道的消息,若频道中存在消息，则会执行回调函数的handleDelivery
        DefaultConsumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //envelope存放生产者信息
                System.out.println("Producer Message:"+ JSON.toJSONString(envelope));

                String message=new String(body,"UTF-8");
                //执行业务
                System.out.println("Consumer Received Message:"+message);
            }
        };
        //RabbitMQ中的消息确认机制——自动回复队列应答
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
