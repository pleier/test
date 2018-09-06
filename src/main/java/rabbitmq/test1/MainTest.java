package rabbitmq.test1;

import java.util.HashMap;

/**
 * @author : pleier
 * @date : 2018/4/4
 */
public class MainTest {

    public MainTest() throws Exception {
        QueueConsumer consumer = new QueueConsumer("testqueue");
        Thread cuThread = new Thread(consumer);

        cuThread.start();

        Producer producer = new Producer("testqueue");
        int i = 0;
        while (i<100) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("tagId", i);
            producer.sendMessage(hm);
            System.out.println("发送第"+i+"消息");
            i++;
        }
        producer.close();
    }

    public static void main(String[] args) throws Exception {
        new MainTest();
    }


}
