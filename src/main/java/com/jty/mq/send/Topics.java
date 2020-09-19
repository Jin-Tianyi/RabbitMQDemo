package com.jty.mq.send;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author :jty
 * @date :20-9-20
 * 主题模式
 * 类似于发布订阅和路由模式
 * 当队列用“ ＃ ”（哈希）绑定键绑定时，它将接收所有消息，而与路由键无关，就像在fanout交换中一样
 * 当在绑定中不使用特殊字符“ * ”（星号）和“ ＃ ”（哈希）时，主题交换的行为就像direct的一样
 * 在定义topics的routingKey时需注意可以使用*,#来匹配值该key值，如key ="x.y.z"时，"x.y.*"、“x.#”均可以匹配到该消息
 * ‘*’可以代替一个单词。
 * ‘＃’可以替代零个或多个单词。
 */
public class Topics {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        //RabbitMQ服务器地址
        factory.setHost("106.14.146.229");
        factory.setVirtualHost("/dev");
        factory.setUsername("dev");
        factory.setPassword("123456");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
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
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
            }

        }
    }
}
