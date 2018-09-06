package rabbitmq.test1;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者
 *
 * @author : pleier
 * @date : 2018/4/4
 */
public class QueueConsumer extends PointToPoint  implements Runnable,Consumer {
    public QueueConsumer(String pointName) throws Exception {
        super(pointName);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void run(){
        try {
            channel.basicConsume(pointName,true,this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void handleConsumeOk(String consumerTag) {
        // TODO Auto-generated method stub
        System.out.println("Consumer "+consumerTag +" registered");

    }

    @Override
    public void handleCancelOk(String consumerTag) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleDelivery(String consumerTag, Envelope env, AMQP.BasicProperties props, byte[] body) throws IOException {
        // TODO Auto-generated method stub
        Map map = (HashMap) SerializationUtils.deserialize(body);
        System.out.println("Message Number "+ map.get("tagId") + " received.");
        //channel.basicAck(env.getDeliveryTag(), false);
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleRecoverOk(String consumerTag) {
        // TODO Auto-generated method stub

    }
}
