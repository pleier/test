package rabbitmq.test1;

import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;

/**
 * 消息生产者
 *
 * @author : pleier
 * @date : 2018/4/4
 */
public class Producer extends PointToPoint {

    public Producer(String pointName) throws Exception {
        super(pointName);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param @param  Object
     * @param @throws IOException    设定文件
     * @return void    返回类型
     * @throws
     * @Title: sendMessage
     * @Description: 生产消息
     */
    public void sendMessage(Serializable Object) throws IOException {
        channel.basicPublish("", pointName, null, SerializationUtils.serialize(Object));
    }
}
