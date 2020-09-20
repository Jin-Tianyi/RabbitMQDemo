package com.jty.mq.receive;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author :jty
 * @date :20-9-20
 * @RabbitListener 该注解用于绑定或监听队列，使用queuesToDeclare定义并绑定队列
 * @Queue 定义队列，可定义多个参数如name、durable、exclusive等
 */
@Component
public class BootReceive {
    /**
     * 简单模式
     */
    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue(value = "hello", durable = "false"))
    public void getSimple(Message message) throws UnsupportedEncodingException {
        //业务逻辑
        System.out.println("收到消息：" + new String(message.getBody(), "UTF-8"));
    }

    /**
     * 工作队列
     */
    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue(value = "bootWorkQueues", durable = "false",autoDelete = "false"))
    public void getWorkQ1(Message message, Channel channel) throws IOException {
        String mes = new String(message.getBody(), "UTF-8");
        try{
            channel.basicQos(1);
            //业务逻辑
            if(mes.contains(".")) {
                //针对m.10休眠10秒
                Thread.sleep(10000);
            }else {
                //否则1秒
                Thread.sleep(1000);
            }
            //手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            //重入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }finally {
            //打印消息
            System.out.println("[getWorkQ1]:"+mes);
        }
    }
    /**
     * 工作队列
     */
    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue(value = "bootWorkQueues", durable = "false",autoDelete = "false"))
    public void getWorkQ2(Message message, Channel channel) throws IOException {
        String mes = new String(message.getBody(), "UTF-8");
        try{
            channel.basicQos(1);
            //业务逻辑
            if(mes.contains(".")) {
                //针对m.10休眠10秒
                Thread.sleep(10000);
            }else {
                //否则1秒
                Thread.sleep(1000);
            }
            //手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            //重入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }finally {
            //打印消息
            System.out.println("[getWorkQ2]:"+mes);
        }
    }
    /**
     * 发布订阅
     * @Queue(value = "bootFanout1")
     */
    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = "bootFanout1",
                    autoDelete = "true", durable = "false"), exchange = @Exchange(value = "bootFanout", type = ExchangeTypes.FANOUT)))
    public void getFanout1(Message message, Channel channel) throws IOException {
        String mes = new String(message.getBody(), "UTF-8");
        try{
            //业务逻辑
            //手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            //重入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }finally {
            //打印消息
            System.out.println("[getFanout1]:"+mes);
        }
    }
    /**
     * 发布订阅
     * @Queue(value = "bootFanout2")
     */
    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = "bootFanout2",
                    autoDelete = "true", durable = "false"), exchange = @Exchange(value = "bootFanout", type = ExchangeTypes.FANOUT)))
    public void getFanout2(Message message, Channel channel) throws IOException {
        String mes = new String(message.getBody(), "UTF-8");
        try{
            //业务逻辑
            //手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            //重入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }finally {
            //打印消息
            System.out.println("[getFanout2]:"+mes);
        }
    }
    /**
     * 路由模式
     * @QueueBinding(key="1")
     */
    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = "bootDirect1",
                    autoDelete = "true", durable = "false"),
                    exchange = @Exchange(value = "bootDirect", type = ExchangeTypes.DIRECT),
            key="1"))
    public void getDirect1(Message message, Channel channel) throws IOException {
        String mes = new String(message.getBody(), "UTF-8");
        try{
            //业务逻辑
            //手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            //重入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }finally {
            //打印消息
            System.out.println("[getDirect1]:"+mes);
        }
    }
    /**
     * 路由模式
     * @QueueBinding(key="2")
     */
    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = "bootDirect2",
                    autoDelete = "true", durable = "false"),
                    exchange = @Exchange(value = "bootDirect", type = ExchangeTypes.DIRECT),
                    key="2"))
    public void getDirect2(Message message, Channel channel) throws IOException {
        String mes = new String(message.getBody(), "UTF-8");
        try{
            //业务逻辑
            //手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            //重入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }finally {
            //打印消息
            System.out.println("[getDirect2]:"+mes);
        }
    }
    /**
     * 路由模式
     * @QueueBinding(key="3")
     */
    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = "bootDirect3",
                    autoDelete = "true", durable = "false"),
                    exchange = @Exchange(value = "bootDirect", type = ExchangeTypes.DIRECT),
                    key="3"))
    public void getDirect3(Message message, Channel channel) throws IOException {
        String mes = new String(message.getBody(), "UTF-8");
        try{
            //业务逻辑
            //手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            //重入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }finally {
            //打印消息
            System.out.println("[getDirect3]:"+mes);
        }
    }
    /**
     * 主题模式
     * @QueueBinding(key="*.*.2") 接受末尾是2的消息
     */
    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = "bootTopic1",
                    autoDelete = "true", durable = "false"),
                    exchange = @Exchange(value = "bootTopic", type = ExchangeTypes.TOPIC),
                    key="*.*.2"))
    public void getTopic1(Message message, Channel channel) throws IOException {
        String mes = new String(message.getBody(), "UTF-8");
        try{
            //业务逻辑
            //手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            //重入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }finally {
            //打印消息
            System.out.println("[getTopic1]:"+mes);
        }
    }
    /**
     * 主题模式
     * @QueueBinding(key="r.k.#") 接受"r.k."开头的消息，无论末尾是什么
     */
    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = "bootTopic2",
                    autoDelete = "true", durable = "false"),
                    exchange = @Exchange(value = "bootTopic", type = ExchangeTypes.TOPIC),
                    key="r.k.#"))
    public void getTopic2(Message message, Channel channel) throws IOException {
        String mes = new String(message.getBody(), "UTF-8");
        try {
            //业务逻辑
            //手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //重入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        } finally {
            //打印消息
            System.out.println("[getTopic2]:" + mes);
        }
    }
}
