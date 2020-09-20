package com.jty.mq.send;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :jty
 * @date :20-9-20
 * RabbitTemplate Spring提供用于发送消息的模板对象，rabbitTemplate自动注入
 */
@RestController
public class BootSend {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /** 简单队列 */
    @GetMapping(value = "testSimple")
    public void testSimple() {
        rabbitTemplate.convertAndSend("hello", "Simple Hello world!");
    }
    /** 工作队列 */
    @GetMapping(value = "/testWork")
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
    /** routingKey 为空*/
    @GetMapping(value = "/testFanout")
    public void testFanout() {
        for (int i = 0; i < 20; i++) {
            String message = "m" + i;

            if (i == 10) {
                message = "m." + i;
            }
            rabbitTemplate.convertAndSend("bootFanout","", message);
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
    /** routingKey 不为空，随即为 1 2 3 */
    @GetMapping(value = "/testDirect")
    public void testDirect() {
        String routingKey;
        String message;
        for (int i = 0; i < 20; i++) {

            //定义routingKey
            if (0 == i % 2) {
                routingKey = "2";
            } else if (0 == i % 3) {
                routingKey = "3";
            } else {
                routingKey = "1";
            }
            message = routingKey + "---" + i;
            rabbitTemplate.convertAndSend("bootDirect",routingKey, message);
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }
    }
    /** routingKey 不为空，随即为 “r.k.2”或 “r.k.1” */
    @GetMapping(value = "/testTopic")
    public void testTopic() {
        String routingKey;
        String message;
        for (int i = 0; i < 20; i++) {
            //定义routingKey
            if (0 == i % 2) {
                routingKey = "r.k.2";
            }  else {
                routingKey = "r.k.1";
            }
            message = routingKey + "---" + i;
            rabbitTemplate.convertAndSend("bootTopic",routingKey, message);
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }
    }
}
