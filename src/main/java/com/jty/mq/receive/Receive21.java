package com.jty.mq.receive;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author :jty
 * @date :20-9-19
 */
public class Receive21 {
    private final static String QUEUE_NAME2 = "workQueues";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.14.146.229");
        factory.setVirtualHost("/dev");
        factory.setUsername("dev");
        factory.setPassword("123456");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME2, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        /**
         *
         * prefetchSize  服务器将交付的最大内容量（以八位字节为单位），如果不受限制，则为0
         * prefetchCount 服务器将传递的最大邮件数，如果没有限制则为0
         * global        如果设置应应用于整个渠道而不是每个消费者，则为true
         * */
        channel.basicQos(1);

        DeliverCallback deliverCallback02 = (consumerTag, delivery) -> {

            String message = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
                //手动确认消息 multiple=false,不批量处理
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            } catch (Exception e) {
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(),false,false);
            } finally {
                System.out.println(" [x] Done");
            }
        };
        channel.basicConsume(QUEUE_NAME2, false, deliverCallback02, consumerTag -> {
        });
    }
    private static void doWork(String task) throws InterruptedException {
        if(task.contains(".")) {
            //针对m.10休眠10秒
            Thread.sleep(10000);
        }else {
            //否则1秒
            Thread.sleep(1000);
        }
    }
}
