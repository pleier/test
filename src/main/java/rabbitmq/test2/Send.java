package rabbitmq.test2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author : pleier
 * @date : 2018/4/4
 */
public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        try {
            /**
             * 创建连接连接到MabbitMQ
             */
            ConnectionFactory factory = new ConnectionFactory();

            //设置rabbitmq服务器地址
            factory.setHost("192.168.3.128");

            //设置rabbitmq服务器用户名
            factory.setUsername("plei");

            //设置rabbitmq服务器密码
            factory.setPassword("plei");

            Connection connection = factory.newConnection();

            /**
             * 创建一个频道
             */
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String message = "------===-=-=-===-=-==-";

            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());

            channel.close();
            connection.close();
        }catch (Exception e){

        }

    }
}
