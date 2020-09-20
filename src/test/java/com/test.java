package com;

import com.jty.mq.RabbitMqApp;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author :jty
 * @date :20-9-20
 * RabbitTemplate Spring提供用于发送消息的模板对象，rabbitTemplate自动注入
 */
@SpringBootTest(classes = RabbitMqApp.class)
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void testSimple() {
        rabbitTemplate.convertAndSend("hello", "Simple Hello world!");
    }
    @Test
    public void testWork() {
        for (int i = 0; i < 20; i++) {
            String message = "m" + i;

            if (i == 10) {
                message = "m." + i;
            }
            rabbitTemplate.convertAndSend("bootWorkQueues", message);
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
    /*@Test
    public void testFanout() {
        rabbitTemplate.convertAndSend("bootFanout","", "bootFanout Hello world!");
    }*/
}
