package com.jty.mq.send;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author :jty
 * @date :20-9-20
 * 发布订阅模式
 * 一次向许多消费者发送消息，每个消费者获取相同的消息
 * 生产者扔给交换机消息，交换机根据自身的类型(fanout)将会把所有消息复制同步到所有与其绑定的队列，每个队列可以有一个消费者
 */
public class PublishAndSubscribe {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        //RabbitMQ服务器地址
        factory.setHost("106.14.146.229");
        factory.setVirtualHost("/dev");
        factory.setUsername("dev");
        factory.setPassword("123456");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            for (int i = 0; i < 20; i++) {
                String message = "m" + i;

                if (i == 10) {
                    message = "m." + i;
                }
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
            }

        }
    }
}
