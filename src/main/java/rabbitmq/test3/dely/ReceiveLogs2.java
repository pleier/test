package rabbitmq.test3.dely;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : pleier
 * @date : 2018/4/8
 */
public class ReceiveLogs2 {
    private final static String EXCHANGE_NAME="my_exchange";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.128");
        factory.setUsername("plei");
        factory.setPassword("plei");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //channel.exchangeDeclare(EXCHANGE_NAME, "x-delayed-message");

        //声明一个随机名字的队列
        //String queueName = channel.queueDeclare().getQueue();

        //绑定队列到路由器上
        channel.queueBind("my-queue",EXCHANGE_NAME,"abc");
        channel.basicQos(1);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"UTF-8");
                System.out.println("消息接收时间: "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                System.out.println(msg);
                System.out.println("-------------------------------------------------------------------");
               channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        channel.basicConsume("my-queue",false,consumer);
    }
}
