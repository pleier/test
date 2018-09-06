package rabbitmq.test3.dely;

import com.rabbitmq.client.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : pleier
 * @date : 2018/4/8
 */
public class EmitLog {
   // private static final String EXCHANGE_NAME = "logs";
    private final static String EXCHANGE_NAME="my_exchange";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.128");
        factory.setUsername("plei");
        factory.setPassword("plei");
        Connection  connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明路由及路由的类型
        Map<String, Object> args1 = new HashMap<>(16);
        args1.put("x-delayed-type", "direct");
        channel.exchangeDeclare(EXCHANGE_NAME, "x-delayed-message", true, false, args1);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date now = new Date();
        Date accept = getAfterMinut(30);

        byte[] messageBodyBytes = ("消息发送时间: "+sf.format(now)).getBytes();
        Map<String, Object> headers = new HashMap(16);
        headers.put("x-delay", accept.getTime()-now.getTime());
        AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder().headers(headers).contentType("text/plain").deliveryMode(2);
        //MessageProperties.PERSISTENT_TEXT_PLAIN
        //AMQP.BasicProperties aaa = props.build();
        channel.basicPublish(EXCHANGE_NAME, "abc", props.build(), messageBodyBytes);
        System.out.println("消息发送时间"+sf.format(now));

        //关闭通道和链接
        channel.close();
        connection.close();
    }

    private static Date getAfterMinut(int m){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,m);
        return calendar.getTime();
    }
}
