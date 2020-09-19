package com.jty.mq.send;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author :jty
 * @date :20-9-20
 * 路由模式
 * 原理上与发布订阅相似，区别在于routingKey相同的消息
 * 发送消息到交换机并且要指定路由key，消费者将队列绑定到交换机时需要指定路由key
 */
public class Routing {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        //RabbitMQ服务器地址
        factory.setHost("106.14.146.229");
        factory.setVirtualHost("/dev");
        factory.setUsername("dev");
        factory.setPassword("123456");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //定义交换机类型为direct
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
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
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
            }

        }
    }
}
