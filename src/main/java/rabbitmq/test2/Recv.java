package rabbitmq.test2;

import com.rabbitmq.client.*;

import java.io.IOException;
/**
 * @author : pleier
 * @date : 2018/4/4
 */
public class Recv implements Runnable,Consumer {

    private static Channel channel;

    //队列名称
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception
    {
        //打开连接和创建频道，与发送端一样
        ConnectionFactory factory = new ConnectionFactory();
        //设置rabbitmq服务器地址
        factory.setHost("192.168.3.128");

        //设置rabbitmq服务器用户名
        factory.setUsername("plei");

        //设置rabbitmq服务器密码
        factory.setPassword("plei");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Recv recv  = new Recv();
        Thread thread = new Thread(recv);
        thread.start();



    }

    @Override
    public void handleConsumeOk(String s) {
        // TODO Auto-generated method stub
        System.out.println("Consumer "+s +" registered");
    }

    @Override
    public void handleCancelOk(String s) {

    }

    @Override
    public void handleCancel(String s) throws IOException {

    }

    @Override
    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    @Override
    public void handleRecoverOk(String s) {

    }

    @Override
    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {

        System.out.println("Message Number "+ new String(bytes) + " received.");
        //channel.basicAck(env.getDeliveryTag(), false);
    }

    @Override
    public void run() {
        try {
            channel.basicConsume(QUEUE_NAME,true,this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
