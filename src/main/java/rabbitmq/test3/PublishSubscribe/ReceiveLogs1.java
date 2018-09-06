package rabbitmq.test3.PublishSubscribe;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author : pleier
 * @date : 2018/4/8
 */
public class ReceiveLogs1 {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.128");
        factory.setUsername("plei");
        factory.setPassword("plei");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        //声明一个随机名字的队列
        String queueName = channel.queueDeclare().getQueue();

        //绑定队列到路由器上
        channel.queueBind(queueName,EXCHANGE_NAME,"");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"UTF-8");
                System.out.println(" [x] Received '" + msg + "'");
            }
        };

        channel.basicConsume(queueName,true,consumer);
    }
}
