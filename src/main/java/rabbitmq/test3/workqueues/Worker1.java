package rabbitmq.test3.workqueues;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author : pleier
 * @date : 2018/4/8
 */
public class Worker1 {
    //队列名称
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.128");
        factory.setUsername("plei");
        factory.setPassword("plei");
        //创建链接
        Connection connection = factory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //为通道指明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //回调消费消息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                } catch (Exception e) {

                } finally {
                    System.out.println("[X] done");
                }
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);

    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch : task.toCharArray()) {
            if(ch=='5'){
                System.exit(1);
            }
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }

}
