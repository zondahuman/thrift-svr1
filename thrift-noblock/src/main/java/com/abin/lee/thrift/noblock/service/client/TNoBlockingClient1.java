package com.abin.lee.thrift.noblock.service.client;

import com.abin.lee.thrift.api.HelloService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by abin on 2017/2/23 13:24.
 * thrift-svr1
 * com.abin.lee.thrift.service.client
 */
public class TNoBlockingClient1 {


    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;

    /**
     *
     * @param userName
     */
    public void startClient(String userName) {
        TTransport transport = null;
        try {
            //使用非阻塞方式，按块的大小进行传输，类似于Java中的NIO。记得调用close释放资源
            transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));
            // 协议要和服务端一致
//            TProtocol protocol = new TBinaryProtocol(transport);
            //高效率的、密集的二进制编码格式进行数据传输协议
            TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            HelloService.Client client = new HelloService.Client(
                    protocol);
            transport.open();
            String result = client.sayHello(userName);
            System.out.println("Thrify client result =: " + result);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        TNoBlockingClient1 client = new TNoBlockingClient1();
        client.startClient("Michael");

    }



}
