package com.jty.mq.send;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;


/**
 * @author :jty
 * @date :20-9-19
 * 工作队列（竞争的消费者模式）
 * 一个生产者，多个消费者，每个消费者获取到的消息唯一，多个消费者只有一个队列
 * 共同争抢当前的消息队列内容,谁先拿到谁负责消费消息
 * 任务分配系统不需知道哪一个任务执行系统在空闲,直接将任务扔到消息队列中,空闲的系统自动争抢
 */
public class WorkQueues {
    private final static String QUEUE_NAME = "workQueues";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        //RabbitMQ服务器地址
        factory.setHost("106.14.146.229");
        factory.setVirtualHost("/dev");
        factory.setUsername("dev");
        factory.setPassword("123456");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            /**
             * queue 队列名称
             * durable 如果为true则声明一个持久队列，则（该队列将在服务器重启后保留下来）
             * exclusive 如果为true则声明一个独占队列（仅限此连接）
             * autoDelete 如果为true则声明一个自动删除队列（服务器将在不再使用它时将其删除）
             * arguments 队列的其他属性（构造参数）
             * */
            //消息持久化 MessageProperties.PERSISTENT_TEXT_PLAIN
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            for (int i = 0; i < 20; i++) {
                String message = "m" + i;

                if (i == 10) {  
                    message = "m." + i;
                }
                channel.basicPublish("", QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}