package com.example.demo.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by 25790 on 2021/12/25.
 */
public class RabbitMQTemplate {
    private static final String routingKey="boot-queue";
    private static RabbitTemplate rabbitTemplate=new RabbitTemplate();

    public static void main(String[] args) {
        RabbitTemplate rabbitTemplate=new RabbitTemplate();
        sendMsg("RabbitMQ Test");

        receiveMsg();
    }

    private static void sendMsg(String msg){
        rabbitTemplate.convertAndSend("",routingKey,msg);
    }

    private static void receiveMsg(){
        Object msg = rabbitTemplate.receiveAndConvert(routingKey);
        System.out.println(JSON.toJSONString(msg));
    }
}
