package rabbitmq.test3.topics;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author : pleier
 * @date : 2018/4/8
 */
public class ReceiveLogsTopic {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception{
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("192.168.3.128");
            factory.setUsername("plei");
            factory.setPassword("plei");
            connection = factory.newConnection();
            channel = connection.createChannel();
            //声明路由及路由的类型
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            String queueName = channel.queueDeclare().getQueue();

            String bingingKeys[] = {"kern.*", "*.critical"};
            for (String bindingKey : bingingKeys) {
                channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
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
        }catch (Exception e){

        }
    }
}
