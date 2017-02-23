package com.abin.lee.thrift.hsha.service.server;

import com.abin.lee.thrift.api.HelloService;
import com.abin.lee.thrift.hsha.service.impl.HelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * Created by abin on 2017/2/23 13:20.
 * thrift-svr1
 * com.abin.lee.thrift.service.server
 */
public class THsHaServer1 {

    public static final int SERVER_PORT = 8090;
//半同步半异步的服务端模型，需要指定为： TFramedTransport 数据传输的方式。
    public void startServer() {
        try {
            System.out.println("HelloService THsHaServer start ....");

            TProcessor tprocessor = new HelloService.Processor<HelloService.Iface>(
                    new HelloServiceImpl());

            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(
                    SERVER_PORT);
            THsHaServer.Args thhsArgs = new THsHaServer.Args(tnbSocketTransport);
            thhsArgs.processor(tprocessor);
            thhsArgs.transportFactory(new TFramedTransport.Factory());
//            thhsArgs.transportFactory(new TFramedTransport.Factory());
            thhsArgs.protocolFactory(new TCompactProtocol.Factory());
//            thhsArgs.protocolFactory(new TBinaryProtocol.Factory());

            //半同步半异步的服务模型
            TServer server = new THsHaServer(thhsArgs);
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
        THsHaServer1 server = new THsHaServer1();
        server.startServer();
    }




}
