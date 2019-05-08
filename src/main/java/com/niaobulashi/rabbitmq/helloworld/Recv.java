package com.niaobulashi.rabbitmq.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @program: javademo
 * @description: rabbitmq接收
 * @author: hulang
 * @create: 2019-05-08 16:51
 **/
public class Recv {
    // 队列名称
    private final static String QUEUE_NAME = "helloMQ";

    public static void main(String[] argv) throws Exception {

        // 打开连接和创建频道，与发送端一样
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // 创建一个连接
        Connection connection = factory.newConnection();
        //创建一个频道
        Channel channel = connection.createChannel();
        //声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}

