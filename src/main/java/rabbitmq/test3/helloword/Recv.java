package rabbitmq.test3.helloword;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author : pleier
 * @date : 2018/4/8
 */
public class Recv {
    //队列名称
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.128");
        factory.setUsername("plei");
        factory.setPassword("plei");
        //创建链接
        Connection connection = factory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //为通道指明队列
        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //回调消费消息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}
