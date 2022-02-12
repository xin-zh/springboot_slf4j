package com.example.demo.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by 25790 on 2021/12/25.
 * 运行需要安装rabbitMQ环境，参看文档：https://www.cnblogs.com/vaiyanzi/p/9531607.html
 */
public class Producer {
    private final static String QUEUE_NAME="rabbitMQ.test";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");
        //2.创建连接
        Connection connection = connectionFactory.newConnection();
        //3.创建通道
        Channel channel = connection.createChannel();
        //4.声明队列
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        String message="RabbitMQ Test!";
        //5.发送消息
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes("UTF-8"));
        System.out.println("Producer Send:"+message);
        channel.close();
        connection.close();

    }
}
