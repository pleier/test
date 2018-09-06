package rabbitmq.test3.PublishSubscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author : pleier
 * @date : 2018/4/8
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.128");
        factory.setUsername("plei");
        factory.setPassword("plei");
        Connection  connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明路由及路由的类型
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String msg = "msg...";

        //发布消息
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + msg + "'");

        //关闭通道和链接
        channel.close();
        connection.close();
    }
}
