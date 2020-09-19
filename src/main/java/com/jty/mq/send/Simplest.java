package com.jty.mq.send;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author :jty
 * @date :20-9-19
 * Simplest模式：简单一对一案例
 */
public class Simplest {
    private final static String QUEUE_NAME = "hello";
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
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
