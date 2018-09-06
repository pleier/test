package rabbitmq.test3.routing;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author : pleier
 * @date : 2018/4/8
 */
public class ReceiveLogsDirect1 {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.128");
        factory.setUsername("plei");
        factory.setPassword("plei");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明路由及路由的类型
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName = channel.queueDeclare().getQueue();

        //定义要监听的级别
        String[] routingKeys = {"error"};

        //根据绑定键绑定
        for (String routingKey : routingKeys) {
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
        }

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
