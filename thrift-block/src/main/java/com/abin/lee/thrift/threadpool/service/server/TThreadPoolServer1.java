package com.abin.lee.thrift.threadpool.service.server;

import com.abin.lee.thrift.api.HelloService;
import com.abin.lee.thrift.threadpool.service.impl.HelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * Created by abin on 2017/2/23 13:20.
 * thrift-svr1
 * com.abin.lee.thrift.service.server
 * 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
 */
public class TThreadPoolServer1 {

    public static final int SERVER_PORT = 8090;

    public void startServer() {
        try {
            System.out.println("HelloService TThreadPoolServer start ....");

            TProcessor tprocessor = new HelloService.Processor<HelloService.Iface>(
                    new HelloServiceImpl());

            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
            TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(
                    serverTransport);
            ttpsArgs.processor(tprocessor);
//            ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
            ttpsArgs.protocolFactory(new TCompactProtocol.Factory());

            // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
            TServer server = new TThreadPoolServer(ttpsArgs);
            server.serve();

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        TThreadPoolServer1 server = new TThreadPoolServer1();
        server.startServer();
    }




}
