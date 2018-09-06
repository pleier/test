package rabbitmq.test1;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author : pleier
 * @date : 2018/4/4
 */
public abstract class PointToPoint {
    protected Channel channel;
    protected Connection connection;
    protected String pointName;

    /**
     * 获取一个队列的连接
     * @param pointName
     * @throws IOException
     */
    public PointToPoint(String pointName) throws Exception{
        this.pointName = pointName;

        //创建连接工厂
        ConnectionFactory cf = new ConnectionFactory();

        //设置rabbitmq服务器地址
        cf.setHost("192.168.3.128");

        //设置rabbitmq服务器用户名
        cf.setUsername("plei");

        //设置rabbitmq服务器密码
        cf.setPassword("plei");

        //获取一个新的连接
        connection = cf.newConnection();

        //创建一个通道
        channel = connection.createChannel();

        //申明一个队列，如果这个队列不存在，将会被创建
        channel.queueDeclare(pointName, false, false, false, null);
    }


    /**
     *
     * @Title: close
     * @Description: 其实在程序完成时一般会自动关闭连接，但是这里提供手动操作的入口，
     * @param @throws IOException    设定文件
     * @return void    返回类型
     * @throws
     */
    public void close() throws Exception {
        this.channel.close();
        this.connection.close();
    }
    }
