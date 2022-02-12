package com.example.demo.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 * Created by 25790 on 2021/12/25.
 */
@Controller
public class RabbitMQController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String sendSms(String sms){
        rabbitTemplate.convertAndSend("","boot-queue",sms);
        System.out.println("send success");
        return null;
    }
}
