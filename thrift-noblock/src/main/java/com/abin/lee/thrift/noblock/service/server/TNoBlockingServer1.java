package com.abin.lee.thrift.noblock.service.server;

import com.abin.lee.thrift.api.HelloService;
import com.abin.lee.thrift.noblock.service.impl.HelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * Created by abin on 2017/2/23 13:20.
 * thrift-svr1
 * com.abin.lee.thrift.service.server
 */
public class TNoBlockingServer1 {

    public static final int SERVER_PORT = 8090;

    public void startServer() {
        try {
            System.out.println("HelloService TNonblockingServer start ....");

            TProcessor tprocessor = new HelloService.Processor<HelloService.Iface>(
                    new HelloServiceImpl());

            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(
                    SERVER_PORT);
            TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(
                    tnbSocketTransport);
            tnbArgs.processor(tprocessor);
            tnbArgs.transportFactory(new TFramedTransport.Factory());
            tnbArgs.protocolFactory(new TCompactProtocol.Factory());

            // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
            TServer server = new TNonblockingServer(tnbArgs);
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
        TNoBlockingServer1 server = new TNoBlockingServer1();
        server.startServer();
    }



}
